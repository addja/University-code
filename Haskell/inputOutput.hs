saluda :: String -> String

saluda nom
	| femeni	= "Hola maca!"
	| otherwise = "Hola maco!"
	where
		femeni = keyletter == 'a' || keyletter == 'A'
		keyletter = last nom

main :: IO ()

main = do
	nom <- getLine
	putStrLn $ saluda nom