-- Map emulator using list comprehension

myMap :: (a -> b) -> [a] -> [b]

myMap f a = [ f x | x <- a ]

--------------------------------------

-- F¡lter emulator using list comprehension

myFilter :: (a -> Bool) -> [a] -> [a]

myFilter f a = [ x | x <- a, f x ]

--------------------------------------

-- ZipWith emulator using list comprehension and zip 

myZipWith :: (a -> b -> c) -> [a] -> [b] -> [c]

myZipWith f a b = [ uncurry f x | x <- zip a b ]

---------------------------------------

-- given two lists genertates a list of pairs such as
-- the first elements is divisible by the second

thingify :: [Int] -> [Int] -> [(Int, Int)]

thingify a b = [ (x,y) | x <- a, y <- b, mod x y == 0]

---------------------------------------

-- given a not null natural, generates the ordered list
-- of all of their factors

factors :: Int -> [Int]

factors a = [ x | x <- [1..(div a 2)]++[a], mod a x == 0 ]