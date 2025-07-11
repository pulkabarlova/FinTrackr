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

rootProject.name = "FinTrackr"
include(":app")
include(":core:data")
include(":core:ui")
include(":features:articles")
include(":features:count")
include(":features:expenses")
include(":features:history")
include(":features:income")
include(":features:settings")
include(":features:splash")
include(":core:domain")
include(":core:model")
include(":features:transaction_action")
