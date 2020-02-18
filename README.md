# popular-movies-app

Second udacity project

This is my first app. I have written it following a lot of different advice I read on line (medium articles, official page, etc), and in particular the Philippe B. Arch app !

The app uses Activities, ViewModels & Live Data, Data Binding, Repository, Room, Retrofit, and Dagger 2.
Even if there is no network, the app will display data saved in DB.

It is divised into 4 main packages :
- common : containing BaseActivity, BaseViewModel and some other common files.
- data : containing a local package (having the different Dao), a model package, a remote package (where all the network requests are) and a repository package.
-di : for the DI
- features : containing the different features, i.e. home (HomeActivity, HomeViewModel, etc), detail, settings.

**In order to make the app working, you must put your API key in the MovieDatasource file present in data/remote/ package.**

PS : There are some questions at the beginning of some classes, I will be happy to have your answers if possible :)

Enjoy !

