lfib m n = m : (lfib n (m+n))
 
fib n = (lfib 0 1) !! n