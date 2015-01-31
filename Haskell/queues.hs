-- Efficient queues in haskell

data Queue a = Queue [a] [a]
    deriving (Show)

-- create queue

create :: Queue a

create = Queue [] []

-- push

push :: a -> Queue a -> Queue a

push a (Queue p1 p2) = Queue p1 (a:p2)

-- pop

pop :: Queue a -> Queue a

pop (Queue (x:xs) p2) 	= Queue xs p2
pop (Queue [] p2)		= Queue (tail $ reverse p2) []

-- top

top :: Queue a -> a

top (Queue (x:xs) p2) 	= x
top (Queue [] p2) = last p2


-- empty

empty :: Queue a -> Bool

empty (Queue [] [])	= True
empty (Queue _ _)	= False

-- Eq Queue

instance Eq a => Eq (Queue a) where
	Queue l1 l2 == Queue r1 r2 = firstQueue == secondQueue
		where
			firstQueue 	= l1 ++ reverse l2
			secondQueue = r1 ++ reverse r2



