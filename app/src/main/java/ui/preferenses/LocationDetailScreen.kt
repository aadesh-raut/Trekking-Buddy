package com.example.trekkingbuddy.ui.preferences

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.trekkingbuddy.R
import com.example.trekkingbuddy.data.ProfileRepository
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource

@Composable
fun LocationDetailScreen(
    locationName: String,
    navController: NavHostController,
    onSave: (String) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // ✅ ALL 20 TREKS — FULL DATA
    val trekInfoMap = mapOf(
        "Kalsubai Peak" to TrekInfo(
            R.drawable.kalsubai,
            "Maharashtra’s highest peak, famous for sunrise views and challenging climbs.",
            "October – February",
            "6–7 hours",
            startingPoints = listOf(
                "The primary and most popular way to trek Kalsubai Peak, the highest peak in Maharashtra, is via the Bari Village route. This moderate-to-difficult 6.6 km trail (one way) involves walking through fields, grasslands, and climbing several steep iron ladders bolted onto rock faces."
                ,"Planning Your Trek\n" +
                        "* Difficulty: The trek is considered moderate to difficult, with steep ascents and several challenging iron ladders.\n" +
                        "* Time Required: It typically takes 3 to 4 hours to ascend and 2-3 hours to descend. The round trip usually takes 6-7 hours in total.\n" +
                        "* Guides & Permits: No official forest permits are needed, but it's advisable to inform the local village head (Sarpanch of Bari village) before starting. You can hire a local guide, especially if trekking at night or during monsoon.\n" +
                        "* Accommodation & Food: Basic food stalls and accommodation options (homestays, camping tents) are available in Bari village and along the trail, provided by locals."
                ,"Best Time to Visit\n" +
                        "The Kalsubai trek is open year-round, with each season offering a different experience.\n" +
                        "* Monsoon (July to September): The peak season for lush green landscapes and numerous waterfalls, but the trail is very crowded, slippery, and views from the summit might be obscured by fog.\n" +
                        "* Post-Monsoon/Flowering Season (September to October): Offers clear views of the surrounding forts and fields covered in a variety of flowers.\n" +
                        "* Winter/Night Treks (November to April): Ideal for night treks to witness the spectacular sunrise from the summit, with cooler temperatures. May and June are pre-monsoon months where you can spot fireflies.",
                "How to Reach Bari Village (Base Point)\n" +
                        "Bari village is the primary base village and is well-connected by road from Mumbai and Pune. \n" +
                        "* By Public Transport (Train + Cab):\n" +
                        "    1. Take a local or express train on the central line to Kasara Railway Station.\n" +
                        "    2. From Kasara station, take a shared taxi or a private cab to Bari village (approx. 1 hour 45 minutes journey).\n" +
                        "* By Private Vehicle:\n" +
                        "    * From Mumbai: Take NH 160 on the Mumbai-Nashik route to Igatpuri, then continue to Ghoti and finally Bari village.\n" +
                        "    * From Pune: Take NH 60 towards Alephata, then continue to Sangamner, Rajur, Bhandardara, and finally Bari village.\n" +
                        "    * Parking is available in the village. ",
                "Essential Packing List\n" +
                        "* Water: Minimum 2-3 liters of water is essential.\n" +
                        "* Footwear: Sturdy trekking shoes with good grip are crucial, especially for the ladder sections.\n" +
                        "* Clothing: Wear full sleeves and long pants to protect from sun/thorns. Carry a light jacket for night treks and a rain jacket/poncho during monsoon.\n" +
                        "* Essentials: Headlamp or torch with extra batteries (for night treks), high-calorie snacks, a first-aid kit, and an identity proof.\n" +
                        "* Optional: Trekking poles for stability, sunscreen, power bank, and a camera. \n"
            )
        ),
        "Harishchandragad" to TrekInfo(
            R.drawable.harishchandragad,
            "Ancient fort with Konkan Kada cliffs, caves, and temples.",
            "September – February",
            "7–8 hours",
            startingPoints = listOf(
                "Harishchandragad fort, a popular trekking destination in the Western Ghats, offers multiple trekking routes catering to different skill levels, each with its unique views and challenges. The primary base villages are Pachnai, Khireshwar, and Belpada/Walhivale.",
                "Planning Your Trek\n" +
                        "* Difficulty: The difficulty varies significantly by route, ranging from easy (Pachnai) to very difficult (Belpada).\n" +
                        "* Time Required: Duration also varies by route, from 2-3 hours (one way for Pachnai) to 8-10 hours (one way for Belpada). A round trip for the moderate Khireshwar route usually takes 8-10 hours in total.\n" +
                        "* Guides & Permits: No official permits are needed, but hiring a local guide is highly recommended, especially for the more challenging routes (Khireshwar, Belpada) or for night treks/monsoon season.\n" +
                        "* Accommodation & Food: Basic food stalls are available in the base villages and local eateries (dhabas) on top of the fort. You can stay in tents (rented or your own) or use the natural caves for shelter.\n",
                "Best Time to Visit\n" +
                        "Harishchandragad is accessible year-round, with the experience changing drastically with the seasons.\n" +
                        "* Post-Monsoon/Winter (August to February): The ideal time with pleasant weather, minimal fog, lush green scenery, and clear views from the summit. Perfect for night treks.\n" +
                        "* Monsoon (June to September): Offers spectacular views of waterfalls, overflowing rivers, and mist. However, the trails become extremely slippery and more challenging.\n" +
                        "* Summer (March to May): The weather is hot and dry, making day treks strenuous, but the clear skies offer excellent stargazing opportunities at night.",
                "How to Reach the Base Villages\n" +
                        "Pachnai Village (Easiest Route)\n" +
                        "* By Public Transport (Train + Bus/Cab):\n" +
                        "    1. Take a train to Kasara Railway Station.\n" +
                        "    2. From Kasara, take a bus or shared taxi to Rajur.\n" +
                        "    3. From Rajur, take a local bus or jeep to Pachnai village.\n" +
                        "* By Private Vehicle:\n" +
                        "    * Accessible via the Mumbai-Nashik highway to Igatpuri/Ghoti, then towards Rajur and Pachnai. Parking is available in the village.\n" +
                        "Khireshwar Village (Moderate Route)\n" +
                        "* By Public Transport (Bus):\n" +
                        "    * Take a public or private bus from Kalyan or Pune towards Alephata via Malshej Ghat.\n" +
                        "    * Get down at the Khubi Phata bus stop, which is a short walk from Khireshwar village.\n" +
                        "* By Private Vehicle:\n" +
                        "    * Accessible via Malshej Ghat road from both Mumbai and Pune sides. Parking is available.\n" +
                        "Belpada/Walhivale Village (Difficult Route)\n" +
                        "* Accessible by bus from Kalyan or Pune via Malshej Ghat. This route is for highly experienced trekkers with technical climbing gear only.\n",
                "Essential Packing List\n" +
                        "* Water: Minimum 2-3 liters of water is essential; refill points are limited on top.\n" +
                        "* Footwear: Sturdy trekking shoes with excellent grip are a must, especially for the rocky/slippery sections.\n" +
                        "* Clothing: Full sleeves and long pants recommended. A jacket or warm layers are necessary for winter/night treks. Rain gear is vital during monsoon.\n" +
                        "* Essentials: Headlamp/torch (for night treks), high-energy snacks, a personal first-aid kit, and an ID proof.\n" +
                        "* Optional: Trekking poles (very helpful), sunscreen, power bank, camera, tent, and sleeping bag (if planning an overnight stay).\n"
            )
        ),
        "Rajgad" to TrekInfo(
            R.drawable.rajgad,
            "Former capital of the Maratha Empire with massive fortifications.",
            "October – March",
            "6 hours",
            startingPoints = listOf(
                "Rajgad fort offers multiple trekking routes from various base villages, each providing a unique experience with varying difficulty levels. The most popular base villages are Gunjavane, Pali, and Bhutunde.",
                "Planning Your Trek\n" +
                        "* Difficulty: The difficulty ranges from easy to moderate, depending on the chosen route. The trail to the Balekilla (citadel) is steep and requires careful navigation.\n" +
                        "* Time Required: It generally takes 2.5 to 4 hours to ascend, depending on the route and your fitness level. The total time required for a day trek (up and down plus exploration) is about 6-8 hours.\n" +
                        "* Guides & Permits: No forest permits are required. However, a local guide is recommended for less popular routes or during the monsoon season for safety.\n" +
                        "* Accommodation & Food: Basic food stalls are available at the base villages and on the fort itself, near the Padmavati temple. You can stay overnight in the Padmavati temple (which can accommodate around 50 people) or pitch your own tent near the temple area. \n",
                "Best Time to Visit\n" +
                        "The Rajgad trek can be done year-round, as each season offers a different charm. \n" +
                        "* Monsoon (June to September): The fort is lush green with many small streams and waterfalls, offering a spectacular view, but the trails are slippery.\n" +
                        "* Post-Monsoon/Winter (October to February): Considered the best time for pleasant weather, clear views of surrounding forts, and ideal for night camping.\n" +
                        "* Summer (March to May): The weather is hot, making day treks strenuous, but offers clear skies and excellent stargazing opportunities at night. \n",
                "How to Reach the Base Villages\n" +
                        "Rajgad is approximately 60-70 km from Pune and well-connected. \n" +
                        "* By Public Transport (Bus/Cab):\n" +
                        "    1. Reach Swargate Bus Station in Pune.\n" +
                        "    2. From Swargate, you can get a direct bus to Gunjavane or Pali village, though the frequency can be low.\n" +
                        "    3. Alternatively, take a bus towards Velhe or Nasrapur and then a shared taxi/auto to the base villages.\n" +
                        "* By Private Vehicle:\n" +
                        "    * From Pune: Head towards Nasrapur via the Pune-Bangalore highway, then take a right turn towards Velhe road to reach Gunjavane or Pali village.\n" +
                        "    * Parking is available in the villages.",
                "Essential Packing List\n" +
                        "* Water: Carry at least 2-3 liters of water. Refill options on the fort are limited.\n" +
                        "* Footwear: Sturdy trekking shoes with good grip are crucial due to rocky patches and steep sections.\n" +
                        "* Clothing: Wear comfortable, full-sleeved clothing. Carry a jacket or warm layers for night stays and a rain jacket for the monsoon.\n" +
                        "* Essentials: Headlamp/torch (for night treks/stays), high-energy snacks, a first-aid kit, and an identity proof.\n" +
                        "* Optional: Trekking poles for stability, sunscreen, camera, tent and sleeping bag (if camping overnight).\n"
            )
        ),
        "Torna Fort" to TrekInfo(
            R.drawable.torna,
            "Historic fort and the first captured by Shivaji Maharaj.",
            "October – February",
            "5–6 hours",
            startingPoints = listOf(
                "Torna fort, one of the largest forts in the Pune district, is a challenging yet rewarding trek with two primary routes from the base village of Velhe.\n",
                "Planning Your Trek\n" +
                        "* Difficulty: The trek is considered moderate to difficult, with steep sections, rock patches, and a relatively long ascent.\n" +
                        "* Time Required: It typically takes 2.5 to 4 hours to ascend and about 2 hours to descend. The total time for the trek is generally 5-7 hours, not including exploration time on the fort.\n" +
                        "* Guides & Permits: No official permits are required. While the main trail is well-marked, hiring a local guide is recommended if you are new to the area, trekking at night, or doing the trek during the monsoon season.\n" +
                        "* Accommodation & Food: Basic food stalls are available at Velhe village. On top of the fort, near the Menghai Devi temple, local villagers sometimes provide simple meals and accommodation in the temple or in tents (for a fee).\n",
                "Best Time to Visit\n" +
                        "The Torna trek offers different experiences depending on the season.\n" +
                        "* Monsoon (June to September): The fort is at its lush green best, with numerous waterfalls and a very misty atmosphere. Trails are extremely slippery and challenging.\n" +
                        "* Post-Monsoon/Winter (October to February): This is the ideal time, with pleasant weather, minimal fog, clear views of the surrounding forts (like Rajgad and Sinhagad), and great for night treks/camping.\n" +
                        "* Summer (March to May): The weather is hot and dry, making day treks physically demanding, but it offers clear skies for stargazing if camping overnight.",
                "How to Reach Velhe Village (Base Point)\n" +
                        "Velhe village is the primary base village for the Torna trek and is located about 50-60 km from Pune.\n" +
                        "* By Public Transport (Bus/Cab):\n" +
                        "    1. Reach Swargate Bus Station in Pune.\n" +
                        "    2. From Swargate, take a ST (State Transport) bus that goes directly to Velhe village. The frequency is limited, so check the schedule in advance.\n" +
                        "    3. Alternatively, take a bus towards Nasrapur or Cheladi and then hire a shared taxi or auto-rickshaw to Velhe.\n" +
                        "* By Private Vehicle:\n" +
                        "    * From Pune: Take the Pune-Bangalore highway (NH48) until Nasrapur. Take a right turn from Nasrapur (after the flyover) and follow the road towards Velhe village.\n" +
                        "    * Parking is available in the village near the start of the trail.\n",
                "Essential Packing List\n" +
                        "* Water: Carry at least 2-3 liters of water. Refill options are scarce on top of the fort.\n" +
                        "* Footwear: Sturdy trekking shoes with good grip are crucial, as there are steep and rocky patches.\n" +
                        "* Clothing: Wear comfortable, full-sleeved clothing. Carry a jacket or warm layers for winter/night stays and a rain jacket/poncho during monsoon.\n" +
                        "* Essentials: Headlamp or torch with extra batteries (for night treks), high-energy snacks, a personal first-aid kit, and an identity proof.\n" +
                        "* Optional: Trekking poles for stability, sunscreen, power bank, and a camera.\n"
            )
        ),
        "Lohagad Fort" to TrekInfo(
            R.drawable.lohagad,
            "Easy and popular fort near Lonavala, ideal for beginners.",
            "June – February",
            "3–4 hours",
            startingPoints = listOf(
                "Lohagad fort is one of the most popular and easiest treks in the Pune district, known for its \"Vinchu Kata\" (Scorpion's sting) spur and twin fort Visapur. The primary base point is Lohagad Wadi / Bhaje Village.\n",
                "Planning Your Trek\n" +
                        "* Difficulty: The trek is considered easy to moderate, well-suited for beginners, families, and casual hikers. The path is mostly well-paved with clear steps for a large portion of the way.\n" +
                        "* Time Required: It typically takes about 1.5 to 2 hours to ascend and roughly 1 hour to descend. The total trekking time is around 3-4 hours.\n" +
                        "* Guides & Permits: No official permits are required. The trail is very popular and straightforward, so a guide is generally not necessary unless you plan to explore surrounding areas or trek at night.\n" +
                        "* Accommodation & Food: Many food stalls and small local eateries are available in Lohagad Wadi and along the trail, offering snacks, tea, and simple meals. There is no official accommodation on the fort, but the Lohagadwadi village at the base has homestays and camping options.\n",
                "Best Time to Visit\n" +
                        "Lohagad can be visited all year round, but the experience is highly dependent on the season.\n" +
                        "* Monsoon (June to September): This is the most popular time to visit. The fort is covered in lush greenery, clouds often settle on the fort, and many small streams and waterfalls emerge. The steps become wet and slightly slippery.\n" +
                        "* Post-Monsoon/Winter (October to February): Offers pleasant weather, clear views of the surrounding Lonavala region and Pawna Lake, and is ideal for night treks or camping.\n" +
                        "* Summer (March to May): The weather is hot and dry, making day treks strenuous, but the clear skies offer excellent visibility and stargazing opportunities.",
                "How to Reach Lohagad Wadi (Base Point)\n" +
                        "Lohagad is conveniently located near Lonavala/Khandala, roughly 60 km from Pune and 100 km from Mumbai.\n" +
                        "* By Public Transport (Train + Cab/Bus):\n" +
                        "    1. Take a local or express train on the central line to Malavli Railway Station.\n" +
                        "    2. From Malavli station, it's a 3-4 km walk or a short auto-rickshaw/cab ride to the base village, Lohagad Wadi or Bhaje village.\n" +
                        "* By Private Vehicle:\n" +
                        "    * From Pune: Take the Mumbai-Pune Expressway or Old Mumbai-Pune Highway (NH48) towards Lonavala. Take the exit/turn towards Bhaje village/Malavli.\n" +
                        "    * From Mumbai: Take the Mumbai-Pune Expressway/NH48 towards Pune. Take the exit at Khandala/Lonavala and proceed towards Malavli/Bhaje village.\n" +
                        "    * Parking is available in the village areas near the starting point of the trek.",
                "Essential Packing List\n" +
                        "* Water: Carry at least 2 liters of water. Refill options are available from food stalls but best to carry your own.\n" +
                        "* Footwear: Sturdy trekking shoes with a good grip are sufficient; chappals/sandals are not recommended due to slippery steps in monsoon.\n" +
                        "* Clothing: Wear comfortable, full-sleeved clothing. A rain jacket is essential during the monsoon, and a light jacket for winter mornings/evenings.\n" +
                        "* Essentials: Personal first-aid kit, high-energy snacks, an identity proof, and a torch if trekking early in the morning or late evening.\n" +
                        "* Optional: Trekking poles (helpful for stability), power bank, and a camera.\n"
            )
        ),
        "Visapur Fort" to TrekInfo(
            R.drawable.visapur,
            "Large plateau fort with scenic Sahyadri views.",
            "June – February",
            "4–5 hours",
            startingPoints = listOf(
                "Visapur fort, a popular destination near Lonavala and the twin fort of Lohagad, offers a scenic and relatively easy-to-moderate trekking experience. The primary base points are Bhaje Village and Patange Wadi, with most trekkers combining it with the Lohagad trek.",
                "Planning Your Trek\n" +
                        "* Difficulty: The trek is considered easy to moderate. The main trail involves ascending a waterfall path during the monsoon, which can be slippery but is manageable for most fitness levels.\n" +
                        "* Time Required: It typically takes about 1.5 to 2.5 hours to ascend and roughly 1 hour to descend. The total time for the trek is around 3-4 hours, not including exploration time.\n" +
                        "* Guides & Permits: No official permits are required. A guide is generally not necessary as the route is straightforward and popular.\n" +
                        "* Accommodation & Food: Food stalls are available at the base villages (Bhaje and Patange Wadi) and a few local vendors sell snacks/water near the top plateau. There are no official staying options on the fort itself, so plan for a day trek. Homestays are available in the base villages.\n",
                "Best Time to Visit\n" +
                        "Visapur fort can be visited all year round, but the monsoon season is the most sought-after time.\n" +
                        "* Monsoon (June to September): The peak season for visiting, as the main ascent path becomes a flowing waterfall, creating a unique and thrilling trekking experience. The fort is covered in lush greenery and mist.\n" +
                        "* Post-Monsoon/Winter (October to February): Offers pleasant weather, clear views of the surrounding hills, Lohagad fort, and Pawna Lake. Ideal for casual hikes and photography.\n" +
                        "* Summer (March to May): The weather is hot and dry, making day treks strenuous, but the clear skies offer excellent visibility.\n",
                "How to Reach the Base Villages\n" +
                        "Visapur is located near Malavli, around 60 km from Pune and 100 km from Mumbai, and is easily accessible.\n" +
                        "* By Public Transport (Train + Cab/Walk):\n" +
                        "    1. Take a local or express train on the central line to Malavli Railway Station.\n" +
                        "    2. From Malavli station, the starting points at Bhaje village or Patange wadi are a short auto-rickshaw/cab ride or a 30-40 minute walk away.\n" +
                        "* By Private Vehicle:\n" +
                        "    * From Pune/Mumbai: Take the Mumbai-Pune Expressway or Old Mumbai-Pune Highway (NH48) towards Lonavala. Take the exit for Malavli/Bhaje Caves.\n" +
                        "    * Parking is available in the villages near the start of the trail.",
                "Essential Packing List\n" +
                        "* Water: Carry at least 2 liters of water.\n" +
                        "* Footwear: Sturdy trekking shoes with excellent grip are crucial, especially for the slippery waterfall route in the monsoon. Avoid sandals or chappals.\n" +
                        "* Clothing: Wear comfortable, quick-drying full-sleeved clothing. A rain jacket is essential during the monsoon.\n" +
                        "* Essentials: A personal first-aid kit, high-energy snacks, an identity proof, and a torch if trekking early or late.\n" +
                        "* Optional: Trekking poles (can be helpful for stability), power bank, and a camera."
            )
        ),
        "Harihar Fort (Trimbakgad)" to TrekInfo(
            R.drawable.harihar,
            "Famous for its steep, rock-cut staircase climb.",
            "October – March",
            "3–4 hours",
            startingPoints = listOf(
                "Harihar fort, known for its unique triangular shape and thrilling rock-cut steps, offers a moderately challenging trek primarily from the base villages of Nirgudpada and Harshewadi. The main attraction is the steep, nearly vertical (80-degree incline) staircase carved into the rock face.",
                "Planning Your Trek\n" +
                        "Difficulty: The trek is considered moderate to tough. While the initial trail is relatively easy, the final section of narrow, steep rock-cut steps (around 117 steps) requires high endurance, a good head for heights, and caution.\n" +
                        "Time Required: It typically takes about 1.5 to 2.5 hours to ascend and roughly 1.5 to 2 hours to descend. The total time required for the trek is generally 4-5 hours, excluding exploration time on the fort.\n" +
                        "Guides & Permits: No official forest permits are required to trek the fort. However, hiring a local guide is recommended, especially for beginners, solo trekkers, or during the monsoon season when the steps can be slippery.\n" +
                        "Accommodation & Food: Basic food stalls and local eateries (dhabas) are available at the base villages and on the plateau just before the steps. Limited accommodation is available in the form of homestays in the villages or natural caves on the fort (accommodating about 10-12 people), but camping on the plateau is also an option. ",
                "Best Time to Visit\n" +
                        "The Harihar trek is open year-round, with the ideal time depending on your preference for scenery and challenge.\n" +
                        "Post-Monsoon/Winter (October to February): This is the ideal time, with pleasant weather, clear skies offering panoramic views of surrounding forts, and is great for night treks/camping.\n" +
                        "Monsoon (June to September): The peak season for lush green landscapes and a thrilling experience as water flows down the rock-cut steps. However, the steps become very slippery and crowded on weekends, so extreme caution and good grip shoes are mandatory.\n" +
                        "Summer (March to May): The weather is hot and dry, making day treks strenuous and less recommended due to lack of shade.",
                "How to Reach the Base Villages\n" +
                        "Harihar fort is located in the Nashik district and is well-connected from both Mumbai and Pune. The two main base villages are Nirgudpada and Harshewadi. \n" +
                        "By Public Transport (Train + Cab/Bus):\n" +
                        "Reach Kasara Railway Station (if coming from Mumbai) or Nashik Road Railway Station (if coming from Nashik side).\n" +
                        "From Kasara, take a shared taxi/jeep to Khodala and then another shared taxi to Nirgudpada village. From Nashik, take a bus to Trimbakeshwar and then a cab to Harshewadi or Nirgudpada.\n" +
                        "By Private Vehicle:\n" +
                        "From Mumbai/Pune: Drive via the Mumbai-Nashik highway (NH160) towards Igatpuri/Ghoti, and then follow local roads to Nirgudpada or Harshewadi village. Parking is available in the villages. \n",
                "Essential Packing List\n" +
                        "Water: Minimum 2-3 liters of water is essential, as refill points are limited on the fort.\n" +
                        "Footwear: Sturdy trekking shoes with excellent grip are crucial for the steep rock-cut steps.\n" +
                        "Clothing: Wear full-sleeved clothing to protect from sun/thorns. Carry a light jacket for winter and rain gear (poncho/raincoat) for the monsoon.\n" +
                        "Essentials: Headlamp or torch with extra batteries (for night treks), high-calorie snacks/dry fruits, a personal first-aid kit, and an identity proof.\n" +
                        "Optional: Trekking poles (can be helpful for stability, especially on the initial trail), sunscreen, power bank, and a camera."
            )
        ),
        "Rajmachi Fort" to TrekInfo(
            R.drawable.rajmachi,
            "Scenic twin forts surrounded by lush greenery.",
            "June – February",
            "5–6 hours",
            startingPoints = listOf(
                "Rajmachi fort, a historically significant fort with two twin peaks (Shrivardhan and Manaranjan), is a very popular trekking and camping destination near Lonavala/Khandala. The primary base village is Udhewadi, and the trek is known for being relatively easy and suitable for all age groups.\n",
                "Planning Your Trek\n" +
                        "Difficulty: The trek is considered easy. The main route from Lonavala is more of a long, flat walk through a scenic plateau, while the route from Kondivade village involves more elevation gain and a traditional climb.\n" +
                        "Time Required: From the Lonavala side, it takes about 4 to 5 hours to reach the fort plateau (approx. 15 km walk). From the Kondivade side, it takes around 2-3 hours (approx. 5 km climb). Exploring the two peaks adds another 1-2 hours.\n" +
                        "Guides & Permits: No official permits are required. The main trails are well-marked and popular, so a guide is not necessary unless you prefer local insights or are trekking at night.\n" +
                        "Accommodation & Food: Many food stalls and local eateries (dhabas) are available in Udhewadi village and along the Lonavala trail, especially on weekends. You can stay overnight in the Kal Bhairavnath temple in the village (basic accommodation), camp in your own tents, or stay in local homestays.\n",
                "Best Time to Visit\n" +
                        "Rajmachi can be visited throughout the year, with the experience changing significantly with the seasons.\n" +
                        "Monsoon (June to September): The most popular time. The area transforms into a lush green paradise with numerous waterfalls, streams, and a very misty atmosphere. The trail from Kondivade is spectacular but the Lonavala road can get very muddy and slippery.\n" +
                        "Post-Monsoon/Winter (October to February): The ideal time for pleasant weather, clear views of the surrounding valleys and Pawna Lake, and perfect for night treks and camping.\n" +
                        "Summer (March to May): The weather is hot and dry, making day treks strenuous, but the clear skies offer excellent stargazing opportunities. May is also popular for firefly sightings before the monsoon begins.",
                "How to Reach the Base Villages\n" +
                        "Rajmachi is located near Lonavala and is easily accessible from both Mumbai and Pune. The two main approaches are from the Lonavala side (easier walk) and the Kondivade side (proper trek).\n" +
                        "By Public Transport (Train + Cab/Walk):\n" +
                        "Reach Lonavala Railway Station (well connected by local/express trains from Mumbai & Pune).\n" +
                        "From Lonavala, you can hire a private jeep to Udhewadi village (note: the road is rough, especially in monsoon) or start the 15 km walk.\n" +
                        "Alternatively, from Lonavala, you can reach the Kondivade base village via local transport (cab/auto) to start the steeper ascent.\n" +
                        "By Private Vehicle:\n" +
                        "From Pune/Mumbai: Drive via the Mumbai-Pune Expressway or Old Mumbai-Pune Highway (NH48) towards Lonavala. Take the exit/turn towards the starting points (either Lonavala road end or Kondivade village side).\n" +
                        "Parking is available in the villages near the starting points.",
                "Essential Packing List\n" +
                        "Water: Carry at least 2 liters of water. Refill options are available in the village.\n" +
                        "Footwear: Sturdy trekking shoes with good grip are sufficient.\n" +
                        "Clothing: Wear comfortable full-sleeved clothing. Carry a jacket or warm layers for winter/night stays and rain gear (poncho/raincoat) for the monsoon.\n" +
                        "Essentials: Headlamp or torch with extra batteries (for night treks), high-energy snacks, a personal first-aid kit, and an identity proof.\n" +
                        "Optional: Trekking poles (helpful for stability), power bank, tent and sleeping bag (if camping overnight), camera.\n" +
                        "\n"
            )
        ),
        "Andharban" to TrekInfo(
            R.drawable.andharban,
            "Dense forest trek with waterfalls and misty trails.",
            "July – September",
            "4–5 hours",
            startingPoints = listOf(
                "The Andharban trek, aptly named the \"Dark Forest\" due to its dense canopy that blocks sunlight, is a unique, mostly descending trail through the Tamhini Ghat region. It starts at Pimpri village and ends near the backwaters of Bhira Dam. \n",
                "Planning Your Trek\n" +
                        "Difficulty: The trek is considered easy to moderate. While the trail is primarily downhill (a 2,100 ft altitude drop), it is a long distance (approx. 13-14 km) and can be very slippery and challenging during the peak monsoon season due to rain, mud, and stream crossings.\n" +
                        "Time Required: It typically takes 4 to 6 hours to complete the main point-to-point trek.\n" +
                        "Guides & Permits: A forest entry permit is compulsory and usually arranged by tour operators if you go with a group. Hiring a local guide is recommended, especially if you are new to the area, as mobile network is zero in the forest, and it is easy to lose the trail.\n" +
                        "Accommodation & Food: There are no food stalls on the trail itself. Basic meals and snacks are available at the base villages (Pimpri and Patnus/Bhira). You can find homestays or camping options in Patnus or Bhira villages, but camping is often banned during the monsoon. ",
                "Best Time to Visit\n" +
                        "The Andharban trek is popular year-round, with the best time being the monsoon and post-monsoon seasons.\n" +
                        "Monsoon (June to September): The most picturesque time, with lush greenery, numerous waterfalls, streams, and dense fog, creating a magical atmosphere. Be prepared for heavy rain, leeches, and very slippery conditions.\n" +
                        "Post-Monsoon/Winter (October to February): Offers pleasant weather, clear views of the Kundalika Valley and Bhira Dam, and is ideal for those who prefer to avoid the heavy monsoon challenges. Summer months are generally too hot and dry for a comfortable trek. ",
                "How to Reach the Base Villages\n" +
                        "The trek typically starts from Pimpri village and ends near Bhira Dam (Patnus village). \n" +
                        "By Public Transport (Train + Cab):\n" +
                        "Take a train to Lonavala Railway Station (the nearest major station).\n" +
                        "From Lonavala, hire a private taxi or take a local bus/shared jeep to Pimpri village (approx. 50 km).\n" +
                        "By Private Vehicle:\n" +
                        "From Pune: The route is via Pirangut, Mulshi Dam, and Tamhini Ghat to Pimpri village (approx. 70 km, 2.5-3 hours drive).\n" +
                        "From Mumbai: The route is via Panvel and the Lonavala road or Mulshi/Tamhini Ghat to Pimpri village (approx. 130-140 km, 4-5 hours drive).\n" +
                        "Note: The start and end points are different, so a private vehicle with a driver arranged for pickup at the end point (Bhira/Patnus) is highly recommended.",
                "Essential Packing List\n" +
                        "Water: Minimum 2-3 liters of water is essential as there are no reliable refill points on the trail.\n" +
                        "Footwear: Sturdy trekking shoes with excellent grip are crucial, especially for the slippery path during monsoon.\n" +
                        "Clothing: Wear full sleeves and long pants to protect from insects and thorns. Carry a rain jacket/poncho in the monsoon and a light jacket for winter.\n" +
                        "Essentials: Headlamp/torch (in case you finish late), high-energy snacks (energy bars, dry fruits), insect repellent (essential during monsoon), a first-aid kit, and an identity proof.\n" +
                        "Optional: Trekking poles (very helpful for stability), power bank, and a camera (in a waterproof bag). "
            )
        ),
        "Ratangad Fort" to TrekInfo(
            R.drawable.ratangad,
            "Jewel of the Sahyadris offering stunning mountain views.",
            "October – February",
            "6 hours",
            startingPoints = listOf(
                "Ratangad fort, an ancient fort located in the Ratanwadi village area, is a moderately challenging trek known for its natural rock peak with a cavity at the top (locally known as 'Nedhe' or 'Eye of the Needle') and the Amruteshwar temple.",
                "Planning Your Trek\n" +
                        "Difficulty: The trek is considered moderate. It involves a steady climb, some forest patches, and a few steep sections with iron ladders that make the ascent manageable.\n" +
                        "Time Required: It typically takes about 2.5 to 3.5 hours to ascend and roughly 2 hours to descend. The total time for the trek is around 5-6 hours.\n" +
                        "Guides & Permits: No official permits are required. While the trail is well-marked and popular, hiring a local guide is recommended for first-timers, solo trekkers, or during night treks/monsoon season, as the route can be confusing near the top.\n" +
                        "Accommodation & Food: Basic food stalls are available in Ratanwadi village and sometimes near the fort entrance. On top of the fort, the natural caves (around 5-6) can accommodate 20-30 people. You can also camp near the fort entrance or stay in homestays in Ratanwadi village.\n",
                "Best Time to Visit\n" +
                        "The Ratangad trek can be enjoyed year-round, with each season offering a unique experience.\n" +
                        "Post-Monsoon/Winter (October to February): This is the ideal time, with pleasant weather, minimal fog, clear views of the surrounding Bhandardara region, Arthur Lake, and the Kalsubai peak range. Perfect for night treks and camping.\n" +
                        "Monsoon (June to September): The fort is covered in lush greenery, clouds, and mist. The Amruteshwar temple is particularly beautiful during this time, but the trail becomes slippery and the iron ladders can be challenging with water flow.\n" +
                        "Summer (March to May): The weather is hot and dry, making day treks strenuous due to lack of shade.",
                "How to Reach Ratanwadi Village (Base Point)\n" +
                        "Ratanwadi village is located deep within the Bhandardara region, approximately 180 km from Mumbai and 160 km from Pune.\n" +
                        "By Public Transport (Train + Bus/Cab/Boat):\n" +
                        "Reach Kasara Railway Station (if coming from Mumbai) or Ahmednagar (if coming from Pune side).\n" +
                        "From Kasara, take a shared taxi/bus to Bhandardara/Shendi.\n" +
                        "From Bhandardara/Shendi, you can take a local bus or shared jeep to Ratanwadi village.\n" +
                        "Monsoon Special: During the monsoon, you can take a boat ride from Bhandardara dam across Arthur Lake to the other side, cutting down road travel time significantly.\n" +
                        "By Private Vehicle:\n" +
                        "From Mumbai/Pune: Drive via the Mumbai-Nashik highway to Igatpuri/Ghoti, then head towards Bhandardara/Shendi, and finally to Ratanwadi village. The last few kilometers are rough. Parking is available in the village.",
                "Essential Packing List\n" +
                        "Water: Minimum 2-3 liters of water is essential, as there are no reliable refill points on the trail.\n" +
                        "Footwear: Sturdy trekking shoes with good grip are crucial, especially for the ladder sections and rocky patches.\n" +
                        "Clothing: Wear full sleeves and long pants. Carry a light jacket for winter and rain gear (poncho/raincoat) for the monsoon.\n" +
                        "Essentials: Headlamp or torch with extra batteries (for night treks/cave stays), high-energy snacks/dry fruits, a personal first-aid kit, and an identity proof.\n" +
                        "Optional: Trekking poles (helpful for stability), sleeping bag/mat (for cave stays/camping), power bank, and a camera."
            )
        ),
        "Korigad Fort" to TrekInfo(
            R.drawable.korigad,
            "Easy fort trek near Lonavala with intact walls and cannons.",
            "June – February",
            "3–4 hours",
            startingPoints = listOf(
                "Korigad is one of the most beginner-friendly treks in Maharashtra, located near Aamby Valley, Lonavala. It is famous for its massive plateau top, intact cannons, and two large ponds.",
                "Planning Your Trek\n" +
                        "Difficulty: Easy. The trek involves a simple walk followed by a series of well-maintained concrete steps. It is suitable for children, seniors, and first-time trekkers.\n" +
                        "Time Required: It takes only 45 minutes to 1 hour to reach the top. You can explore the entire perimeter of the fort in an additional 45 minutes.\n" +
                        "Guides & Permits: No permits are required, and the path is so well-defined that a guide is unnecessary.\n" +
                        "Accommodation & Food: Small stalls at the base village (Peth Shahpur) serve local snacks and pithla-bhakri. There are no official stay facilities on the fort, but the Korai Devi temple on top can provide basic shelter for 5-7 people. Most visitors do this as a day trip from Lonavala.",
                "Best Time to Visit (2025)\n" +
                        "Monsoon (June to September): The most popular time. The fort turns incredibly green, and the steps often have water flowing down them like a small waterfall.\n" +
                        "Winter (October to February): Ideal for a pleasant morning hike with clear views of the Aamby Valley cityscape and the surrounding Sahyadri ranges.\n" +
                        "Summer (March to May): Can be hot during the day as the plateau is very exposed, but the evening breeze on top is pleasant. \n",
                "How to Reach Peth Shahpur (Base Point)\n" +
                        "Korigad is situated about 20 km from Lonavala and is easily accessible from both Mumbai and Pune. \n" +
                        "By Public Transport (Train + Bus/Cab):\n" +
                        "Take a train to Lonavala Railway Station.\n" +
                        "From Lonavala station, take a bus heading toward Aamby Valley and get down at Peth Shahpur village.\n" +
                        "Alternatively, hire a private auto-rickshaw or taxi from the Lonavala market directly to the base.\n" +
                        "By Private Vehicle:\n" +
                        "From Mumbai/Pune: Drive toward Lonavala via the Express Highway. Follow the road toward Aamby Valley City. The base village, Peth Shahpur, is located just before the Aamby Valley entrance gate.\n" +
                        "There is ample parking space available at the start of the trail.",
                "Essential Packing List\n" +
                        "Water: 1-2 liters is sufficient as the trek is short.\n" +
                        "Footwear: Sports shoes with decent grip are fine, though trekking shoes are better during the monsoon when steps can be mossy.\n" +
                        "Clothing: Light, breathable clothing. Carry a raincoat during monsoon as it gets very windy and rainy on the open plateau.\n" +
                        "Essentials: Sunscreen and a hat (the top has very little shade), basic first-aid kit, and an ID proof.\n" +
                        "Optional: A camera for the panoramic views of the valley and a power bank"
            )
        ),
        "Peb Fort (Vikatgad)" to TrekInfo(
            R.drawable.peb,
            "Short trek near Neral with valley views.",
            "October – February",
            "3 hours",
            startingPoints = listOf(
                "Peb fort, also known as Vikatgad, is a small but scenic fort located near Neral, Matheran. The trek is particularly known for its narrow trail, dense forest cover, and a peak that resembles an elephant's trunk.\n",
                "Planning Your Trek\n" +
                        "Difficulty: The trek is considered moderate. While not a long trek, it involves some steep patches, narrow sections that require careful footing, and a short final rock-climbing patch with ropes (though this can be bypassed). It is not recommended for absolute beginners during peak monsoon.\n" +
                        "Time Required: It typically takes about 2.5 to 3 hours to ascend and roughly 2 hours to descend.\n" +
                        "Guides & Permits: No official permits are required. However, a local guide is highly recommended, especially for first-timers or during the monsoon, as the trail can be confusing at certain junctions in the dense forest.\n" +
                        "Accommodation & Food: There are no food stalls or staying facilities on the fort itself. You should carry all your own food and water. The nearest base points like Neral or Matheran town have hotels and restaurants.",
                "Best Time to Visit (2025)\n" +
                        "The Peb fort trek can be done year-round, with the best time being the post-monsoon and winter seasons.\n" +
                        "Post-Monsoon/Winter (October to February): The ideal time with pleasant weather, minimal fog, and clear views of the Matheran hills and surrounding areas.\n" +
                        "Monsoon (June to September): The fort is covered in lush greenery and offers beautiful waterfall views. However, the trail becomes very slippery, challenging, and is often infested with leeches, requiring extra caution.\n" +
                        "Summer (March to May): The weather is hot and dry, making the trek strenuous due to lack of shade in many parts of the trail.\n",
                "How to Reach the Base Point\n" +
                        "The trek has multiple starting points, primarily from Neral or near Matheran.\n" +
                        "By Public Transport (Train + Walk/Cab):\n" +
                        "Take a local or express train to Neral Railway Station (on the central line).\n" +
                        "From Neral station, the starting point for the main trail is about a 30-40 minute walk or a short auto-rickshaw ride away.\n" +
                        "Alternatively, you can reach Matheran via the toy train or taxi, and start the trek from a point near the Matheran railway track.\n" +
                        "By Private Vehicle:\n" +
                        "From Mumbai/Pune: Drive toward Neral town via the Mumbai-Pune Expressway/NH48. You can reach the base starting point near the railway line via local roads.\n" +
                        "Parking is available in Neral town or near the starting points.",
                "Essential Packing List\n" +
                        "Water: Minimum 2-3 liters of water is essential as there are no refill points on the trail or fort.\n" +
                        "Footwear: Sturdy trekking shoes with excellent grip are crucial for the steep and narrow patches.\n" +
                        "Clothing: Wear full sleeves and long pants to protect from insects and thorns. Carry a light jacket for winter and rain gear (poncho/raincoat) for the monsoon.\n" +
                        "Essentials: A headlamp or torch, high-energy snacks (energy bars, dry fruits), insect repellent (essential during monsoon), a personal first-aid kit, and an identity proof.\n" +
                        "Optional: Trekking poles (can be helpful for stability on steep sections), power bank, and a camera.\n" +
                        "\n"
            )

        ),
        "Garbett Point (Matheran)" to TrekInfo(
            R.drawable.garbett,
            "Beautiful plateau trek with panoramic views.",
            "October – March",
            "4 hours",
            startingPoints = listOf(
                "Garbett Point, located near Matheran, offers a scenic and relatively long trek that culminates in panoramic views of the plateau, the surrounding hills, and the Ulhas river valley.\n",
                "Planning Your Trek\n" +
                        "Difficulty: The trek is considered moderate. While not extremely steep, it's a long distance (around 8-10 km) that involves walking through a plateau, ascending a hill, and crossing a few streams.\n" +
                        "Time Required: It typically takes about 3 to 4 hours to ascend and roughly 2 hours to descend. The total trekking time is around 5-6 hours.\n" +
                        "Guides & Permits: No official permits are required. However, a local guide is highly recommended as the trail can be confusing in the initial sections and during the monsoon, and mobile network is patchy.\n" +
                        "Accommodation & Food: There are no food stalls on the trail itself until you reach the Matheran plateau. You must carry all your own food and water. Accommodation options (hotels, homestays) are available in the nearby towns of Neral, Karjat, and Matheran itself.\n",
                "Best Time to Visit \n" +
                        "The Garbett Point trek is best enjoyed during the monsoon and post-monsoon seasons.\n" +
                        "Monsoon (June to September): The most popular and picturesque time. The trail is lush green, you'll encounter many small streams and waterfalls, and the weather is cool and misty. The route can be slippery and muddy, requiring good grip footwear.\n" +
                        "Post-Monsoon/Winter (October to February): This is an ideal time for pleasant weather, clear views of the valley, and a comfortable trekking experience.\n" +
                        "Summer (March to May): The weather is hot and dry, making the trek strenuous due to a lack of shade in many parts of the trail.",
                "How to Reach the Base Point\n" +
                        "The trek typically starts from a point near the Diksal village or the Bhivpuri Road area.\n" +
                        "By Public Transport (Train + Walk/Cab):\n" +
                        "Take a local or express train on the central line to Bhivpuri Road Railway Station or Neral Railway Station.\n" +
                        "From Bhivpuri Road station, the starting point of the trek (near Diksal village) is about a 20-30 minute walk or a short auto-rickshaw ride away.\n" +
                        "By Private Vehicle:\n" +
                        "From Mumbai/Pune: Drive toward Karjat via the Mumbai-Pune Expressway/NH48 or the old highway. Take the turn towards Bhivpuri/Diksal area.\n" +
                        "Parking is available in the villages near the starting points.",
                "Essential Packing List\n" +
                        "Water: Minimum 2-3 liters of water is essential as there are no refill points on the trail until Matheran.\n" +
                        "Footwear: Sturdy trekking shoes with excellent grip are crucial, especially for the rocky and slippery patches during monsoon.\n" +
                        "Clothing: Wear full sleeves and long pants to protect from insects and thorns. Carry a rain jacket/poncho in the monsoon and a light jacket for winter.\n" +
                        "Essentials: A headlamp or torch, high-energy snacks (energy bars, dry fruits), a personal first-aid kit, and an identity proof.\n" +
                        "Optional: Trekking poles (can be helpful for stability), power bank, and a camera."
            )

        ),
        "Karnala Fort" to TrekInfo(
            R.drawable.karnala,
            "Historic fort combined with a bird sanctuary.",
            "October – February",
            "4 hours",
            startingPoints = listOf(
                "Karnala Fort, located within the Karnala Bird Sanctuary near Panvel, is a popular trek famous for its distinctive 125-foot basalt pillar (the thumb) and its status as a protected natural habitat.",
                "Planning Your Trek\n" +
                        "Difficulty: Easy to Moderate. The trail is well-defined and shaded by dense forest for most of the way, making it accessible for beginners and children.\n" +
                        "Time Required: It typically takes 1.5 to 2.5 hours to reach the top. The total round trip usually takes 4-5 hours.\n" +
                        "Guides & Permits: Since it is inside a Bird Sanctuary, you must pay an entry fee at the forest office gate. You do not need a guide as the path is clearly marked with signs.\n" +
                        "Accommodation & Food: No overnight stay is allowed inside the sanctuary or on the fort. Basic snacks and water are available at the base gate. It is best to carry your own food as there are no stalls once you start the ascent.",
                "Best Time to Visit (2025)\n" +
                        "Monsoon (June to September): The best time for lush greenery and spotting small waterfalls. The forest is vibrant, though the trail can be slightly muddy.\n" +
                        "Winter (October to February): The peak season for bird watching. Many migratory birds visit the sanctuary during these months. The weather is cool and perfect for trekking.\n" +
                        "Summer (March to May): It can be quite humid due to the forest cover, and the uphill climb can be tiring in the heat.",
                "How to Reach the Sanctuary (Base Point)\n" +
                        "Karnala is located on the Mumbai-Goa Highway (NH66) and is very easy to reach from Mumbai and Pune. \n" +
                        "By Public Transport (Train + Bus/Auto):\n" +
                        "Take a local train on the Harbour Line to Panvel Railway Station.\n" +
                        "From Panvel ST Bus Stand (nearby), take any bus heading towards Pen, Alibaug, or Mahad and ask to be dropped at the Karnala Bird Sanctuary Gate.\n" +
                        "Alternatively, hire a private auto-rickshaw or taxi from Panvel station directly to the gate (approx. 12 km).\n" +
                        "By Private Vehicle:\n" +
                        "From Mumbai: Drive towards Panvel and continue on the Mumbai-Goa Highway (NH66). The sanctuary entrance is right on the highway, about 10-12 km past Panvel.\n" +
                        "From Pune: Drive via the Mumbai-Pune Expressway, exit towards Panvel, and then take the NH66 towards Goa.\n" +
                        "Paid parking is available at the sanctuary entrance.",
                "Essential Packing List\n" +
                        "Water: 2 liters is mandatory as there are no water sources on the trail.\n" +
                        "Footwear: Sports shoes with good grip or trekking shoes are sufficient.\n" +
                        "Clothing: Breathable, full-sleeved clothes are recommended to avoid mosquito bites in the forest. Carry a raincoat during monsoon.\n" +
                        "Essentials: Insect/mosquito repellent (crucial for this forest trail), a personal first-aid kit, and an ID proof for the entry gate.\n" +
                        "Pro Tip: Do not carry plastic bags or litter; the forest guards are very strict about plastic as it is a protected sanctuary."
            )

        ),
        "Tikona Fort" to TrekInfo(
            R.drawable.tikona,
            "Distinct triangular fort overlooking Pawna Lake.",
            "June – February",
            "3–4 hours",
            startingPoints = listOf(
                "Tikona fort, also known as Vitandgad, is a prominent hill fort near Kamshet and Pawna Lake. It is one of the easiest and most popular treks in the region, known for its triangular shape and a large entrance gate.\n",
                "Planning Your Trek\n" +
                        "Difficulty: Easy. The trail is well-defined and suitable for beginners, families, and casual hikers. It involves some steady climbing and narrow steps near the top.\n" +
                        "Time Required: It typically takes only 1 to 1.5 hours to ascend and roughly 45 minutes to descend. The entire trek can be done in 3-4 hours.\n" +
                        "Guides & Permits: No permits are required, and the path is very clear, so a guide is generally not necessary.\n" +
                        "Accommodation & Food: Basic food stalls are available at the base village (Tikona Peth / Prabhalwadi) on weekends, selling snacks and drinks. There is limited space in a cave and the Trimbakeshwar Mahadev temple on top for an overnight stay (very basic), but camping at the base or nearby Pawna Lake is a better option.",
                "Best Time to Visit \n" +
                        "Tikona fort can be visited all year round, but the experience is best in specific seasons.\n" +
                        "Monsoon (June to September): The fort is at its lush green best, with clouds settling in the valley and stunning views of Pawna Lake. The steps can be slightly slippery, so caution is needed.\n" +
                        "Post-Monsoon/Winter (October to February): This is the ideal time for pleasant weather, clear views of the surrounding forts (Tung, Lohagad, Visapur), and the Pawna Dam backwaters. Perfect for night treks.\n" +
                        "Summer (March to May): The weather is hot and dry, making the day trek strenuous, but the clear skies offer excellent stargazing opportunities if camping overnight.",
                "How to Reach Tikona Peth (Base Point)\n" +
                        "Tikona fort is located about 60 km from Pune and is easily accessible from Mumbai via the expressway.\n" +
                        "By Public Transport (Train + Cab/Bus):\n" +
                        "Take a local or express train to Kamshet Railway Station (on the central line).\n" +
                        "From Kamshet station, take a local bus or shared auto-rickshaw to Tikona Peth / Prabhalwadi village (approx. 20 km).\n" +
                        "By Private Vehicle:\n" +
                        "From Pune: Drive towards Kamshet via the old Mumbai-Pune Highway (NH48) or the Expressway service road, and then follow local roads towards Pawna Lake and Tikona Peth village.\n" +
                        "From Mumbai: Drive via the Mumbai-Pune Expressway (NH48), take the Kamshet exit, and then follow local roads to the base village.\n" +
                        "Parking is available in the village areas near the starting point of the trek.",
                "Essential Packing List\n" +
                        "Water: Carry at least 1-2 liters of water, as there are no reliable refill points on the fort.\n" +
                        "Footwear: Sports shoes with good grip are generally sufficient; trekking shoes are better for the monsoon season.\n" +
                        "Clothing: Wear comfortable, full-sleeved clothing. A light jacket might be needed for winter mornings/evenings. A rain jacket is essential during the monsoon.\n" +
                        "Essentials: A personal first-aid kit, high-energy snacks, an identity proof, and a torch if trekking early or late.\n" +
                        "Optional: Trekking poles (helpful for stability), sunscreen, power bank, and a camera."
            )

        )
    )

    val trek = trekInfoMap[locationName]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // 🔙 Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🖼 Trek Image
        trek?.let {
            Image(
                painter = painterResource(it.imageRes),
                contentDescription = locationName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📍 Trek Name
        Text(
            text = locationName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 📝 Description
        Text(
            text = trek?.description ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ⏱ Info
        Text("🗓 Best Season: ${trek?.bestSeason}")
        Text("⏱ Duration: ${trek?.duration}")

        Spacer(modifier = Modifier.height(32.dp))

        // ℹ Routes
        Text(
            text = "ℹ Trek Information & Routes",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        trek?.startingPoints?.forEach { info ->
            Text(
                text = info,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🏔️ MARK AS COMPLETED
        Button(
            onClick = {
                ProfileRepository.markTrekAsCompleted(
                    trekId = locationName,
                    trekName = locationName
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mark as Completed 🏔️")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📍 START LIVE TRACKING
        Button(
            onClick = {
                navController.navigate("live_tracking")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Start Live Tracking 📍")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ CANCEL / SELECT
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    scope.launch {
                        saveSelectedLocationToFirebase(locationName)
                        onSave(locationName)
                    }
                }
            ) {
                Text("Select")
            }
        }
    }

}

data class TrekInfo(
    val imageRes: Int,
    val description: String,
    val bestSeason: String,
    val duration: String,
    val startingPoints: List<String>

)









