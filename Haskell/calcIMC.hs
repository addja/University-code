-- Reads from input the name of a person, his weigth and height,
-- calculates his IMC and outputs the interpretation

calcIMC :: Float -> Float -> String
calcIMC w h
	| val < 18	= "magror"
	| val <= 25  = "corpulencia normal"
	| val <= 30  = "sobrepes"
	| val <= 40  = "obesitat"
	| otherwise = "obesitat morbida"
		where val = w / (h * h)


getIMCdata :: [String] -> String
getIMCdata [w,h] = calcIMC (read w::Float) (read h::Float)


processLine :: [String] -> String
processLine (x:xs) = x ++ ": " ++ (getIMCdata xs)


reader :: [String] -> [String]
reader text = map processLine $ map words text


parser :: String -> String
parser input = unlines $ reader $ init $ lines input


main :: IO ()
main = do
	input <- getContents
	putStr $ parser input