package com.panjikrisnayasa.submission3.`object`

import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.model.User

object UserData {
    private val userData = arrayOf(
        arrayOf(
            "JakeWharton",
            "Jake Wharton",
            R.drawable.user1,
            "Google, Inc.",
            "Pittsburgh, PA, USA",
            102,
            56995,
            12
        ),
        arrayOf(
            "amitshekhariitbhu",
            "AMIT SHEKHAR",
            R.drawable.user2,
            "@MindOrksOpenSource",
            "New Delhi, India",
            37,
            5153,
            2
        ),
        arrayOf(
            "romainguy",
            "Romain Guy",
            R.drawable.user3,
            "Google",
            "California",
            9,
            7972,
            0
        ),
        arrayOf(
            "chrisbanes",
            "Chris Banes",
            R.drawable.user4,
            "@google working on @android",
            "Sydney, Australia",
            30,
            14725,
            1
        ),
        arrayOf(
            "tipsy",
            "David",
            R.drawable.user5,
            "Working Group Two",
            "Trondheim, Norway",
            56,
            788,
            0
        ),
        arrayOf(
            "ravi8x",
            "Ravi Tamada",
            R.drawable.user6,
            "AndroidHive | Droid5",
            "India",
            28,
            18628,
            3
        ),
        arrayOf(
            "jasoet",
            "Deny Prasetyo",
            R.drawable.user7,
            "@gojek-engineering",
            "Kotagede, Yogyakarta, Indonesia",
            44,
            277,
            39
        ),
        arrayOf(
            "budioktaviyan",
            "Budi Oktaviyan",
            R.drawable.user8,
            "@KotlinID",
            "Jakarta, Indonesia",
            110,
            178,
            23
        ),
        arrayOf(
            "hendisantika",
            "Hendi Santika",
            R.drawable.user9,
            "@JVMDeveloperID @KotlinID @IDDevOps",
            "Bojongsoang - Bandung Jawa Barat",
            1064,
            428,
            61
        ),
        arrayOf(
            "sidiqpermana",
            "Sidiq Permana",
            R.drawable.user10,
            "Nusantara Beta Studio",
            "Jakarta Indonesia",
            65,
            465,
            10
        )
    )

    val listUserData: ArrayList<User>
        get() {
            val listUser = arrayListOf<User>()
            for (tUserData in userData) {
                val user = User()
                user.username = tUserData[0] as String
                user.name = tUserData[1] as String
                user.avatar = tUserData[2] as Int
                user.company = tUserData[3] as String
                user.location = tUserData[4] as String
                user.repositoryCount = tUserData[5] as Int
                user.followerCount = tUserData[6] as Int
                user.followingCount = tUserData[7] as Int
                listUser.add(user)
            }
            return listUser
        }
}