-- Practica obligatioria de haskell - Daniel Otero Avalle

-- Signature, Position and Substitution

type Signature = [(String, Int)]

type Position = [Int]

type Substitution a = [(a,a)]


-- Rewrite class

class Eq a => Rewrite a where
	getVars		:: a -> [a]
	valid 		:: Signature -> a -> Bool
	match 		:: Eq a => a -> a -> [(Position, Substitution a)]
	apply		:: a -> Substitution a -> a
	replace		:: a -> [(Position, a)] -> a
	evaluate	:: a -> a


-- Rewrite rules and RewriteSystem

data Rule a = Rule a a

type RewriteSystem a = [Rule a]

instance Show a => Show (Rule a) where
    show (Rule l r) = show l ++ " --> " ++ show r


-- validRule and validRewriteSystem

validRule :: Rewrite a => Signature -> Rule a -> Bool
validRule s (Rule l r) = 
	valid s l && valid s r && contained (getVars l) (getVars r)
		where
			contained l xs = foldr (\ x -> (&&) (elem x l)) True xs

validRewriteSystem :: Rewrite a => Signature -> RewriteSystem a -> Bool
validRewriteSystem s as = foldr ((&&) . validRule s) True as


-- strategy

type Strategy a = [(Position, a)] -> [(Position, a)]


-- oneStepRewrite

oneStepRewrite :: Rewrite a => RewriteSystem a -> a -> Strategy a -> a
oneStepRewrite rewsys obj strat = replace obj (strat instr)
	where
		instr = concat (parser rewsys obj) 

		parser :: Rewrite a => RewriteSystem a -> a -> [[(Position, a)]]	
		parser rs obj 	= map (\x -> adapt (ruleToSubs x) obj) rs   

		adapt :: Rewrite a => Substitution a -> a -> [(Position, a)]
		adapt s o 	= adapter (snd $ head s) (match (fst $ head s) o)

		adapter :: Rewrite a => a -> [(Position, Substitution a)] -> [(Position, a)]
		adapter y l = map (\x -> (fst x, apply y (snd x))) l

		ruleToSubs :: Rewrite a => Rule a -> Substitution a
		ruleToSubs (Rule l r) = [(l,r)]

parser :: Rewrite a => RewriteSystem a -> a -> [[(Position, a)]]	
parser rs obj 	= map (\x -> adapt (ruleToSubs x) obj) rs

adapt :: Rewrite a => Substitution a -> a -> [(Position, a)]
adapt s o 	= adapter (snd $ head s) (match o (fst $ head s))

adapter :: Rewrite a => a -> [(Position, Substitution a)] -> [(Position, a)]
adapter y l = map (\x -> (fst x, apply y (snd x))) l

ruleToSubs :: Rewrite a => Rule a -> Substitution a
ruleToSubs (Rule l r) = [(l,r)]


-- rewrite

rewrite :: Rewrite a => RewriteSystem a -> a -> Strategy a -> a
rewrite rewsys obj strat 
	| new /= obj 	= rewrite rewsys new strat
	| otherwise		= obj
		where 
			new = evaluate (oneStepRewrite rewsys obj strat)


-- nrewrite

nrewrite :: Rewrite a => RewriteSystem a -> a -> Strategy a -> Int -> a
nrewrite rewsys obj strat num
	| num /= 0	= nrewrite rewsys new strat (num-1)
	| otherwise	= new
		where 
			new = evaluate (oneStepRewrite rewsys obj strat)


-- ########## RString ###########

data RString = RString String deriving Eq

instance Rewrite RString where

	getVars rs = []

	valid sig (RString (s:st)) 	= accepted sig [s] && valid sig (RString st)
		where
			accepted :: Signature -> String -> Bool
			accepted [] r 			= False
			accepted ((c, i):xs) r 	= c == r || accepted xs r

	match (RString x) (RString y) = matcher x y (length x) (length y) 0
		where
			matcher :: String -> String -> Int -> Int -> Int -> [(Position, Substitution RString)]
			matcher x "" lx ly pos = []
			matcher x y lx ly pos
				| ly == pos 	= []
				| fragment == x = 
					([pos], [(RString fragment, RString residual)]) : matcher x residual lx ly (pos+lx)
				| otherwise 	= matcher x (tail y) lx ly (pos+1)
					where
						fragment = take lx y
						residual = drop lx y

	apply (RString x) [(RString l, RString r)]	= RString (x ++ r) 

	replace (RString x) []					= RString x
	replace (RString x) [([y], RString z)]	= RString (replacer x z y 0) -- Position is [y] and just one replace beacuse we work with strings
		where
			replacer :: String -> String -> Int -> Int -> String
			replacer (x:xs) z y i
				| i == y 	= z
				| otherwise = x : replacer xs z y (i+1)

	evaluate rs = rs

-- readString and readRStringSystem

readRString :: String -> RString
readRString s = RString s

readRStringSystem :: [(String, String)] -> RewriteSystem RString
readRStringSystem l = map (\(x,y) -> Rule (RString x) (RString y)) l

-- RString instance of Show

instance Show RString where
    show (RString s) = show s

-- RString strategies

leftmost :: Strategy RString
leftmost []	= []
leftmost (x@(p, RString s):xs) = analize xs (head p) x 	-- On strings position always has just 1 element
	where
		analize [] z var	= [var]
		analize (z@(p, RString s):zs) bound var
			| small < bound 	= analize zs small z
			| otherwise		= analize zs bound var
				where small = head p 								-- On strings position always has just 1 element


rightmost :: Strategy RString
rightmost []	= []
rightmost (x@(p, RString s):xs) = analize xs (head p) x 	-- On strings position always has just 1 element
	where
		analize [] z var	= [var]
		analize (z@(p, RString s):zs) bound var
			| big > bound 	= analize zs big z
			| otherwise		= analize zs bound var
				where big = head p 									-- On strings position always has just 1 element




-- ########## RTree ###########

data RTerm = Var (String, Int) | Num (String, Int) | Simbol (String, Int) [RTerm] deriving (Eq, Ord)

instance Rewrite RTerm where

	getVars (Var v) = [Var v]
	getVars (Num n) = []
	getVars (Simbol op l) = concatMap getVars l

	valid sig (Var v) = True
	valid sig (Num n) = True
	valid sig (Simbol op l) =  predefOp || sigOp || foldl (\y z -> valid sig z || y) True l
		where
			predefOp = op == ("+",2) || op == ("*",2)
			sigOp = elem op sig

	match t1 t2 = matcher t1 t2 []
		where
				matcher t1 t2@(Var v) pos
					| matching 	= [(pos,[(t1,Var v)])]
					| otherwise = []
				matcher t1 t2@(Num n) pos
					| matching 	= [(pos,[])]
					| otherwise = []
				matcher t1 t2@(Simbol op st) pos
					| matching	= (pos,[(t1,t2)]) : searchMore
					| otherwise	= searchMore
					where
						searchMore = concatMap (\x -> matcher t1 (fst x) (pos++[snd x])) (zip st nats)
						nats = iterate (+1) 0

				matching = isMatch t1 t2

				isMatch (Var v) t2 = True
				isMatch (Num n1) (Num n2) = n1==n2
				isMatch (Simbol op1 st1) t2@(Simbol op2 st2)
					| op1 == op2 && st1 == st2 = True
					| otherwise = foldl (\x y -> x || isMatch y t2) False st1
				isMatch t1 t2 	= False 

	apply (Var v) l
		| vars == []     = Var v
		| otherwise     = snd (head vars)
		where 
			vars = filter (\x -> fst x == Var v) l
	apply (Num n) subs = Num n
	apply (Simbol op st) l = Simbol op (foldl (\x y -> x ++ [apply y l]) [] st)

	replace obj subs = foldl (\x y -> replacer x y) obj subs
		where
			replacer :: RTerm -> (Position,RTerm) -> RTerm
			replacer t ([],newt) = newt
			replacer (Simbol op st) (pos:np,newt) = (Simbol op newsubt)
				where
					newsubt = take (pos-1) st ++ (replacer (st!!pos) (np,newt)) : drop pos st

	evaluate (Var v) = Var v
	evaluate (Num n) = Num n
	evaluate (Simbol op st)
		| (fst op == "+" || fst op == "*") && allNum	= Num (operate evaluation,-2)
		| otherwise 									= Simbol op evaluation
		where
			evaluation = map evaluate st

			allNum = all isNum evaluation

			operate :: [RTerm] -> String
			operate l
				| fst op == "+" = show (foldl (\x y -> x + toNum y) 0 l)
				| otherwise 	= show (foldl (\x y -> x * toNum y) 1 l)

			isNum :: RTerm -> Bool
			isNum (Num n) 	= True
			isNum t 		= False

			toNum :: RTerm -> Int
			toNum (Num (s,i)) = read s :: Int


-- readRTree and readRTreeSystem

readRTree :: [(String,Int)] -> RTerm
readRTree rt = fst $ reader rt
	where
		reader :: [(String,Int)] -> (RTerm,[(String,Int)])
		reader (x:xs)
		    | snd x == -1       = (Var x, xs)
		    | snd x == -2       = (Num x, xs)
		    | otherwise         = (Simbol x sons, nxs)
			    where 
			    	(sons, nxs) = subreader ([],xs) (snd x)

			    	subreader :: ([RTerm],[(String,Int)]) -> Int -> ([RTerm],[(String,Int)])
			    	subreader some 0 	= some
			    	subreader (f,s) i 	= subreader (f ++ [fst (reader s)], snd (reader s)) (i-1)

readRTermSystem :: [([(String,Int)],[(String,Int)])] -> RewriteSystem RTerm
readRTermSystem l = map (\(x,y) -> Rule (readRTree x) (readRTree y)) l

-- RTree instance of Show

instance Show RTerm where
    show (Var (s,i)) 			= s
    show (Num (s,i)) 			= s
    show (Simbol (s,i) [])		= s
    show (Simbol (s,i) (x:xs)) 	= s ++ " (" ++ show x ++ concatMap (\y -> ", " ++ show y) xs ++ ")"


-- RTree strategies

parallelinnermost :: Strategy RTerm
parallelinnermost l = analize l []
	where 
		analize [] sol 		= sol
		analize (x@(p, rt):xs) sol = analize xs updatedSol
			where
				updatedSol
					| test 		= filteredSol ++ [x]
					| otherwise = filteredSol

				filteredSol = filter (\x -> p /= take (length p) (fst x)) sol
				test = any (\x -> fst x == take (length (fst x)) p) filteredSol 

leftmostinnermost :: Strategy RTerm
leftmostinnermost [] =[]
leftmostinnermost l = [minimum (parallelinnermost l)]

paralleloutermost :: Strategy RTerm
paralleloutermost l = analize l []
		where 
		analize [] sol 		= sol
		analize (x@(p, rt):xs) sol = analize xs updatedSol
			where
				updatedSol
					| test 		= filteredSol ++ [x]
					| otherwise = filteredSol

				filteredSol = filter (\x -> fst x /= take (length (fst x)) p) sol
				test = any (\x -> p == take (length p) (fst x)) filteredSol 

leftmostoutermost :: Strategy RTerm
leftmostoutermost [] = []
leftmostoutermost l = [minimum (paralleloutermost l)]