package com.cafe_bazaar.venue.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

class Extensions {
    companion object {

        fun NavController.navigateSafe(direction: NavDirections) {
            currentDestination?.getAction(direction.actionId)?.let { navigate(direction) }
        }

        fun NavController.navigateSafe(direction: NavDirections, navOptions: NavOptions?) {
            currentDestination?.getAction(direction.actionId)?.let {
                navigate(
                    direction.actionId,
                    direction.arguments,
                    navOptions
                )
            }
        }
    }
}