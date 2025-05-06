# Pokedex App
- MVI
- Multi-module
- Dagger2
- Room
- Retrofit
- OkHttp
- Paging3
- Cicerone
- Kotlin Serialization
- Coil

# Modules
- : app - the main application module, contains: AppComponent, MainActivity, PokemonApp
- : core:common - shared module, contains: utilities and constants
- : core:data - common data module, contains: common repository PokemonRepository, contains DataComponent, ConnectivityCheckerModule
- : core:di - module for Dagger2 contracts, models for the network
- : core:domain - application domain layer
- : core:navigation - navigation module, contains: AppsScreensModule, NavigationModule
- : core:ui - common module for presentation layers :feature, contains: strings, styles and ViewModelFactoryModule
- : data:database - module for working with the Room database, contains: converters, dao-interfaces, paged datasources, DatabaseComponent, entity, mappers
- : data:network - module for working with the network using Retrofit, contains: NetworkComponent, PokemonService, paged network datasources, mappers
- : feature:pokemon_list - feature for displaying the main list of the application, contains: PokemonListComponent, usecase interfaces, usecaseimpl, PokemonAdapter, and other MVI components
- : feature:pokemon_details - feature for detailed information about the Pokemon, contains: PokemonDetailsComponent, usecase interfaces, usecaseimpl and other MVI components