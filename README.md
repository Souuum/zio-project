## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can run it with `MainAPP.scala` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).


# Reading from the Spotify API & Creating the CSV Files

To read from the spotify API we created 2 classes - ```APICalls.scala & WriterCSV.scala```.

## WriterCSV.scala:
In this class, we have established a variable representing the path where the CSV files will be generated with the filled data (```src/main/resources```).

### Next we created 3 methods:
The first one is ```open```         → It is used to generate a CSV file and subsequently open it, allowing the data writing process into the file.\
The second one is ```writeInCSV```  → It is used to Write an Array of Strings inside the CSV file that we opened.\
The third one is ```close```        → It is used to close the CSV file and save it inside the path already mentioned.

## APICalls.scala:
In this class we created the method ```extractToken``` that extract the token from the spotify API.\
After getting the token from the previous method, we need it as a string so that it can be used in the other Spotify API calls (Getting Tracks/Albums/Artists).\
That's where the method ```extractTokenFromString``` is useful.

After retrieving our token, we need to retrieve the Tracks. that's why we are using the method: ```writeTracksInCSV```.\
This method is used to get the Tracks from the Spotify API using the token created previously.

After getting all the Tracks, we need to change the result into a json file so that it will be easier to navigate and extract the important information.\
That's why we are using the ```extractContent``` method.\
After casting it, we use the WriterCSV class already mentioned so that we can create the file Tracks.csv inside our resources.

In our Spotify API Call, we are asking to get multiple tracks information; that's why we are using a foreach loop.\
Each loop retrieve the information needed ```("id", "name", "popularity", "explicit", "external_urls", "id_album", "id_artists")``` of each track and then write them into the CSV (Tracks.csv)

We are doing the same operations for ```Albums & Artists```.

# Reading CSV

## CsvReaderBatch.scala
In this object, we have to read the CSV file created with the Spotify API.

### We created two methods: 
The first one is ```readCSV``` → It is used to read the CSV file.\
The second one is ```beginRead``` → It is used to begin to have the path of the file.
