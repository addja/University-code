-- flattens a list of lists
flatten :: [[Int]] -> [Int]

flatten l = concat l

-- returns the length of a list
myLength :: String -> Int

myLength l = foldr (\x y -> y+1) 0 l

-- reverses a word
myReverse :: [Int] -> [Int]

myReverse l = foldl (\x y -> (y:x)) [] l

-- counts the number of instances of a number in list of lists
-- and makes a list with them
countIn :: [[Int]] -> Int -> [Int]

countIn l n = foldl (\y z -> y++[length (filter (== n) z)]) [] l

-- takes the first word of a String
firstWord :: String -> String

firstWord l = takeWhile (/= ' ') (dropWhile (== ' ') l)