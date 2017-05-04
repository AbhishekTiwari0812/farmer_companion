package com.example.abhishek.farmer_companion;

import java.util.HashMap;

/**
 * Created by Abhishek on 28-11-2016.
 * Text to be shown goes here.
 * Use correct naming convention to position the text.
 * format (crop name)_(section number)_( index number )
 * section number and index number are 0-based indices.
 */

public class TextInfoClass {
    HashMap<String, String> textInfo;

    TextInfoClass() {
        textInfo = new HashMap<>();
        //textInfo.put("wheat_0_0", "The cool and sunny winters with temperatures ranging from 20 to 25 degrees are very conducive for the growth of wheat. In the state of Punjab, this roughly translates to the period from 25 October to 25 December with the earlier period being better");
        textInfo.put("wheat_0_0", "20 से 25 डिग्री से लेकर तापमान के साथ शांत और धूप सर्दियों गेहूं के विकास के लिए बहुत अनुकूल हैं। पंजाब के राज्य में, यह मोटे तौर पर पहले की अवधि में बेहतर होने के साथ 25 अक्टूबर से 25 दिसंबर तक की अवधि के लिए तब्दील");

        //textInfo.put("wheat_1_0", "Wheat grows on all kinds of soil except for the highly deteriorated alkaline and water logged soils. To test if your soil is alkaline, add some vinegar to your soil. If you observe bubbles in the soil, it means that it is alkaline and hence should not be used for wheat cultivation");
        textInfo.put("wheat_1_0", "गेहूं अत्यधिक खराब क्षारीय और पानी लॉग इन मिट्टी के अलावा मिट्टी के सभी प्रकार पर बढ़ता है। अगर आपके मिट्टी क्षारीय है परीक्षण करने के लिए, अपनी मिट्टी के लिए कुछ सिरका जोड़ें। आप मिट्टी में बुलबुले का निरीक्षण करते हैं, तो इसका मतलब है कि यह क्षारीय है और इसलिए गेहूं की खेती के लिए इस्तेमाल नहीं किया जाना चाहिए");

        //textInfo.put("wheat_1_1", "These are the most prominent wheat varieties in the state of Punjab. All these varieties need to be irrigated and cannot be rainfed. The varieties can be split into two parts. One is timely sown and the other is late sown. The timely sown ones lead to a very good high yield whereas the late sown ones lead to a medium yield.");
        textInfo.put("wheat_1_1", "ये पंजाब राज्य में सबसे प्रमुख गेहूं किस्में हैं। इन सभी किस्मों सिंचित होने की जरूरत है और वर्षा आधारित नहीं किया जा सकता। किस्मों को दो भागों में विभाजित किया जा सकता है। एक समय पर बोया जाता है और अन्य देर से बोई जाती है। समय पर बोया लोगों को एक बहुत अच्छा उच्च उपज के लिए नेतृत्व जबकि देर से बोए वाले एक मध्यम उपज पैदा होती हैं।");

        //textInfo.put("wheat_2_0", "The crops most favourably to be rotated along with the plantation of wheat are rice, cotton, maize, potato and gobhi. Potato and Gobhi are especially useful for weed control");
        textInfo.put("wheat_2_0", "फसलों सबसे अनुकूल गेहूं के वृक्षारोपण के साथ-साथ घुमाया जा करने के लिए चावल, कपास, मक्का, आलू और gobhi हैं। आलू और गोभी खरपतवार नियंत्रण के लिए विशेष रूप से उपयोगी होते हैं");

        textInfo.put("wheat_1_2", "The fertilizer application should normally be on the basis of soil test. In case the facility for soil testing is not available, fertilizer may be applied at the following rates:\nN = 50 kg/acre ;    P2O5 = 25 kg/acre;       K2O = 12 kg/acre ");
        textInfo.put("wheat_1_3", "Drill half dose of N and whole of P and K at sowing and broadcast the remaining N with the first irrigation. If nitrogen is to be applied in the form of urea, apply half of the dose just before pre-sowing irrigation (rauni) or with the final preparatory tillage, the second dose withing 7 days before or upto 5 days after first irrigation. In light soils, apply half nitrogen at the time of sowing, one-fourth nitrogen immediately after first irrigation and the remaining one-fourth nitrogen after second irrigation.Note :\n1. Reduce the nitrogen dose to one-half if the field has been green manured in the preceding kharif season.\n2. Apply 25% less nitrogen dose to wheat sown after leguminous crops.\n 3. To the crop sown in kallar soil, apply 25% more nitrogen than recommended. ");
        textInfo.put("wheat_1_4", "Whole of Phosphorous and Postassium fertilizer has to be applied at the time of sowing. Potassium in soils are low in regions of Gurdaspur, Hoshiarpur, Ropar and Shaheed Bhagat Singh Nagar. Wheat is more responsive to phosphorus application than kharif crops. Hence apply phosphorus to wheat and omit its application to following kharif crop.");
        textInfo.put("wheat_2_1", "Sow wheat after a heavy pre-sowing irrigation (10 cm) except when it follows rice. In case wheat sowing is likely to be delayed due to the late harvest of rice, the pre-sowing irrigation for wheat can be given to standing rice 5-10 days (depending upon soil type) before its harvest except where the crop is to be harvested with combine. This practise advances the sowing of wheat by about a week. For efficient use of irrigation water, farmers are advised to make 8 plots per acre in heavy textured soils and 16 plots per acre in light textured soils.\nThe first irrigation should be relatively light and given three weeks to October-sown crop and after four weeks to the crop sown later. The subsequent irrigations are also determined by the date of sowing. ");
        textInfo.put("wheat_2_2", "Termites : Social insects that live underground in colonies; attack young seedlings as well as grown up plants; the attacked plants wither and ultimately die.\nHow to Control : Treat the seed at the rate of 4ml Dursban/Ruban/Durmet 20 EC (chlorpyriphos) or 6 ml Regent 5% SC (fipronil) per kg seed. Dilute 160ml of Dursban/Ruban/Durmet or 240 ml of Regent in one litre of water and spray the same on 40kg seed spread as a thing layer on the PUCCA ground or tarpauling or polyethene. One can also control it my mixing 5% Aldrin or Chlordane dust with the soil at the time of sowing or during preparation of the land for bowing. ");
        textInfo.put("wheat_2_3", "Wheat aphids : Nymphs and adults sukck sap from leaves, tender shoots and immature rain; They are extremely fast and form large colonies. It damage the crop resulting in discoloration of leavers. Spray 40ml of imidacloprid 200 SL or 20g of thiamethoxam 25 WG or 12 g of Dantop (clothianidin 50 WDG) or 150 ml of Rogor 30 EC (dimethoate) or Metasystox 25 EC in 80-100 litres of water per acre using knap sack sprayer.");
        textInfo.put("wheat_3_0", "1.) Match leaf colour of youngest fully exposed leaf from the 10 randomly selected insect/disease free wheat plants from each field with LCC before 2nd irrigation.\n2.)  If the greenness of 6 or more out of 10 leaves is less than shade 4 on LCC, broadcast 40 kg urea per acre for timely sown wheat and 25 kg urea per acre for late sown wheat with second irrigation.");
        textInfo.put("wheat_3_1", "3.) If greenness is equal to or more than shade 4 on LCC apply only 25 kg urea per acre for timely sown wheat and 15 kg urea per acre for late sown wheat with second irrigation.\n4.)  Always compare colour of the wheat leaf with LCC under shade of your body. ");
        textInfo.put("wheat_3_2", "Zinc deficiency generally appears in light soils under intensive cropping. If recommended dose of zinc sulphate has been applied to the kharif crop, its application may be omitted to the following wheat crop. Paddy responds more to zinc application, therefore zinc should be applied to paddy and its residue is sufficient to meet the requirement of the following wheat crop. However, if zinc has not been applied to paddy and zinc deficiency is expected/appears whose symptoms are a stunted or bushy crop with leaves chlorotic in the middle, which later break and keep hanging.");
        textInfo.put("wheat_3_3", "Apply 25kg of zinc sulphate (21%) per acre will be enough for 2-3 years. Zinc deficiency can also be corrected by foliar spray of 0.5% zinc sulphate (21% Zinc). Prepare the solution for spray by dissolving 1kg zinc sulphate and 1/2 kg unslaked lime in 200 litres of water. This solution is sufficient for spraying an acre of wheat once. Two or three sprays at 15-days intervals are needed.  ");
        textInfo.put("wheat_3_4", "Here, we show the difference between Zinc deficiency and other phenomenon and how differentiate between them. ");
        textInfo.put("wheat_4_0", "Weeds can be controlled via two methods : Cultural control of weeds and Chemical control of weeds. We begin with cultural control and give example of Gullidanda which is a common and demanding weed. ");
        textInfo.put("wheat_4_1", "The infestation of weeds particularly Gullidanda/Sitti (Phalaris Minor) in wheat can be reduced/minimized by early sowing (last week of October/1st week of November) and by rotating wheat with Berseem, Potato based cropping system, raya, gobhi, sarson, winter maize etc. ");
        textInfo.put("wheat_4_2", "Growth and development of Gullidanda can also be suppressed by sowing wheat at 15.0 cm spacings and also by selecting quick growing wheat varieties like PBW 502, PBW 343, WH 542 etc. Preparation of soil mulch after seed bed preparation also helps in eliminating first flush of Gullidanda. Give first hoeing before the application of first irrigation and second hoeing after the first irrigation with wheel hoe, Kasola or Khurpa etc. ");
        textInfo.put("wheat_4_3", "1. Control of Broad Leaf Weeds : Broad-leaf weeds can be controlled with 2,4-D. In fields infested with Bathu (chenopodium album), spray 2,4-D sodium salt (80%) or 2,4-D ethyl ester (36%) at the rate of 250g or 250ml in 200 litres of water per acre with knapsack sprayer. Nomor, a brand formulation of 2,4-D ethyl ester at 250 ml/acre is also equally effective against Bathu. To the crop sown at the normal time (October and November), apply 2,4-D, 35 to 45 days after sowing and to late sown crop (especially sown during December), spray 2,4-D 45 to 55 days after sowing. Spray when the crop is at maximum tillering stage but before the jointing stage. ");
        textInfo.put("wheat_4_4", "When hardy broadleaf weeds especially Kandiali Palak is present in wheat, use Algrip/Algrip Royal (metsulfron 20WP) at 10g/acre in 100 litres of water, 30-35 days after sowing wheat crop. This herbicide provides good control of all those broad leaf weeds which can not be controlled by 2,40D application. ");
        textInfo.put("wheat_5_0", "The crop is harvested when the grains become hard and the straw becomes yellow, dry and brittle. Most of the harvesting in India is done with sickle. ");
        textInfo.put("wheat_5_1", "Harvesting and Threshing should be done only when the crop is fully ripe to avoid grain shattering. The harvesting of wheat can be started five days earlier than dead ripe stage without adverse effect on the yield or quality of grains. ");
        textInfo.put("wheat_5_2", "However, in recent years in some states like Punjab, Haryana, Uttar Pradesh etc. harvesting & threshing is done by combine harvester at a large scale. The maturity time of the crop differs from zone to zone and so is the harvesting. Tractor-operated vertical conveyer reaper windrowers can also be used for harvesting. Use power thresher with proper safety devices to prevent accidents. For good performance, operate these machines at cylider speed recommended for wheat and also observe safety precautions against accidents. The syndicator type (Toka type) can be used to threst the wheat crop with moisture content up to 20%  ");
        textInfo.put("wheat_5_3", "Grain combines can also be used for simultaneous harvesting and threshing of wheat. To facilitate the use of grain combine and to reduce breakdown, dismental the bunds and channels immediately after the last irrigation. Delayed harvesting results in high grain losses. ");
        textInfo.put("wheat_5_4", "Threshing is generally done by various types of threshers which are common in different parts of the country. ");

    }

    String getText(String name) {
        String s = "";
        if (textInfo.containsKey(name))
            s = textInfo.get(name);
        else s = "Text is missing for this item";
        return s;
    }

}