pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Pokemon"
include(":app")
include(":core:common")
include(":core:ui")
include(":core:navigation")
include(":core:data")
include(":data:network")
include(":data:database")
include(":feature:pokemon_list")
include(":feature:pokemon_detail")
include(":core:domain")
include(":core:di")
