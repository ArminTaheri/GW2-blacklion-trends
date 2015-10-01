# GW2-blacklion-trends
This is a command line application that samples random buy and sell prices for items
in the Guild Wars 2 Blacklion Market.


# Getting the runnable JAR
You can download a standalone compiled JAR from
http://cs.mcgill.ca/~ataher5/downloads/gw2bltrends.jar

# Usage
```
java -jar gw2bltrends.jar "gw2_data.sq3" 10
```
You should get an output such as
```
Sampling Guild Wars 2 trading post data with 10 samples.
"Elapsed time: 27234.288687 msecs"
```

And the data is stored in a sqlite database file ready to be used in what ever
application you choose.
