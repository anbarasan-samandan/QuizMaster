package com.droid.quizmaster;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.content.ContentProvider;
import android.provider.BaseColumns;
import android.util.Log;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.BaseColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anbu on 16-12-2018.
 */

public class MemoryContentProvider extends ContentProvider {

    public static final String englishGrammar = "[{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"He threw a stone ________ the river.\",\"answers\":[\"into\",\"across\",\"between\",\"on\"],\"ansIndex\":\"2\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"I'm afraid ________ snake.\",\"answers\":[\"of\",\"to\",\"on\",\"with\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"I'm suffering _______ severe headache.\",\"answers\":[\"with\",\"to\",\"from\",\"and\"],\"ansIndex\":\"3\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Kolkata is situated ______ the banks _______ river Hooghly.\",\"answers\":[\"on\",\"of\",\"in\",\"a and b\"],\"ansIndex\":\"4\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"They visited Kerala ________ the summer holidays.\",\"answers\":[\"during\",\"in\",\"on\",\"from\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"India is playing _______ Australia in the world cup. \",\"answers\":[\"with\",\"against\",\"in\",\"on\"],\"ansIndex\":\"2\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"We took shelter _______ the tree.\",\"answers\":[\"on\",\"above\",\"under\",\"below\"],\"ansIndex\":\"3\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"We rowed _______ the lake.\",\"answers\":[\"across\",\"in\",\"between\",\"with\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Our plane is flying _______ the clouds.\",\"answers\":[\"into\",\"above\",\"around\",\"none\"],\"ansIndex\":\"2\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Ramu is running ________ the dog.\",\"answers\":[\"after\",\"with\",\"in front of\",\"below\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Sumathi is walking __________ the seashore\",\"answers\":[\"with\",\"on\",\"along\",\"into\"],\"ansIndex\":\"3\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Smita belongs ______ a family\",\"answers\":[\"to\",\"with\",\"from\",\"none\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"The mouse was killed _________ our servant.\",\"answers\":[\"by\",\"with\",\"the\",\"a\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"I'm sorry _______ the delay\",\"answers\":[\"on\",\"with \",\"to\",\"for\"],\"ansIndex\":\"4\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Take ______ some sugar ______ the large tin.\",\"answers\":[\"out\",\"in\",\"from\",\"a, c\"],\"ansIndex\":\"4\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"He lives _________ Viduthalai Nagar _____ Chennai\",\"answers\":[\"at\",\"in\",\"on\",\"a, b\"],\"ansIndex\":\"4\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Shall we play ______ 4PM?\",\"answers\":[\"on\",\"at\",\"to\",\"before\"],\"ansIndex\":\"2\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"I will start my badminton practice ________ tomorrow.\",\"answers\":[\"from\",\"by\",\"with\",\"on\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"I greeted him _______ folded hands\",\"answers\":[\"with\",\"on\",\"together\",\"none of the above\"],\"ansIndex\":\"1\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Pradeep went ______ the park _____ his dog\",\"answers\":[\"with\",\"to\",\"a, b\",\"b, a\"],\"ansIndex\":\"4\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"I have invited my friends ________ the birthday party.\",\"answers\":[\"in\",\"for\",\"with\",\"to\"],\"ansIndex\":\"2\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Convey my greetings ________ your Mother.\",\"answers\":[\"for\",\"with\",\"on\",\"to\"],\"ansIndex\":\"4\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"Rani ate slowly\",\"answers\":[\"Adverb of degree\",\"Adverb of number\",\"Adverb of manner\",\"Adverb of time\"],\"ansIndex\":\"3\"},{\"subject\":\"English\",\"category\":\" Grammar\",\"subcategory\":\"General\",\"ask\":\"The servant went upstairs.\",\"answers\":[\"Adverb of manner\",\"Adverb of time\",\"Adverb of place\",\"Adverb of degree\"],\"ansIndex\":\"3\"}]";
    public static final String generalKnowledge = "[{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Lodhi Garden is in _________\",\"answers\":[\"Srinagar\",\"Delhi\",\"Bengaluru\",\"Mysore\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Brindavan Garden is in __________\",\"answers\":[\"Delhi\",\"Allahabad\",\"Mysore\",\"Srinagar\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"A famous garden in Mumbai?\",\"answers\":[\"Tulip Garden\",\"Lal Bagh\",\"Company Garden\",\"Hanging Garden\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"A famous garden in Bengaluru?\",\"answers\":[\"Lal Bagh\",\"Lodhi Garden\",\"Tulip Garden\",\"Hanging Garden\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Tulip Garden is in ________\",\"answers\":[\"Allahabad\",\"Mysore\",\"Delhi\",\"Srinagar\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Rock Garden is in ____________\",\"answers\":[\"Chandigarh\",\"Mysore\",\"Delhi\",\"Allahabad\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"____________ is one of the seven wonders of the world\",\"answers\":[\"Lodhi Garden\",\"Hanging Garden of Mumbai\",\"Hanging Gardens of Babylon\",\"Lal Bagh of Bengaluru\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which characteristics best describe ants \",\"answers\":[\"Work as a team\",\"Travel long distances and remember their way back\",\"Take care of their families\",\"All the above\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal use complex sounds to communicate and coordinate activities among the group very effectively\",\"answers\":[\"Octopus\",\"Whale\",\"Ant\",\"Lizard\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal can recognize themselves in a mirror?\",\"answers\":[\"Whale\",\"Octopus\",\"Snake\",\"Dolphin\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal jump high out of the water in aquatic shows?\",\"answers\":[\"Shark\",\"Clown\",\"Dolphin\",\"Starfish\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal use colours and patterns to communicate with each other?\",\"answers\":[\"Octopus\",\"Whale\",\"Dolphin\",\"None of the above\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal can remember their friends or foes for 50 years?\",\"answers\":[\"Lion\",\"Tiger\",\"Cheetah\",\"Elephant\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal can communicate from long distance?\",\"answers\":[\"Crocodile\",\"Elephant\",\"Tiger\",\"Jagur\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which animal or insect can recognize colours and taste of flowers\",\"answers\":[\"Butterfly\",\"Honeybee\",\"Mosquito\",\"None of the above\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What is the coloured part of the human eye that controls light passing through the pupil?\",\"answers\":[\"Rods\",\"Cones\",\"Iris\",\"Eyelid\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What is the substance nails made of?\",\"answers\":[\"Potassium\",\"Sodium\",\"Chlorine\",\"Keratin\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which is the human body's biggest organ?\",\"answers\":[\"Digestive System\",\"Circulatory System\",\"Nervous System\",\"Skin\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"How many lungs does the human body have?\",\"answers\":[\"One\",\"Two\",\"Three\",\"Four\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What is the name of the voice box in your body?\",\"answers\":[\"Speaker\",\"Phone\",\"Pharynx\",\"Larynx\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The human brain can read up to ______ words per minute\",\"answers\":[\"1000\",\"2000\",\"100\",\"200\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What are the two holes in your nose?\",\"answers\":[\"Nose plugs\",\"Nose entry points\",\"Nostrils\",\"All the above\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which part allows you to experience the tastes such as sweet, bitter and salt?\",\"answers\":[\"Taste buds\",\"Saliva\",\"Tongue\",\"Mouth\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What are the bones that make up your spine called?\",\"answers\":[\"Spinal cord\",\"Vertebrae\",\"Spine bones\",\"None of the above\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What is the flow of blood through heart and around the body called?\",\"answers\":[\"Blood cot\",\"Blood Platelets\",\"Circulation\",\"Mediatation\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What are the bones around your chest that protects heart such as the heart called?\",\"answers\":[\"Chest bones\",\"Frontal bones\",\"Lungs\",\"Ribs\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What is the name of the long pipe that shifts food from the back of your throat to your stomach?\",\"answers\":[\"Trachea\",\"Oesophagus\",\"Epiglottis\",\"Lungs\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The official residence of the President of India\",\"answers\":[\"The Red fort\",\"The Rashtrapati Bhavan\",\"White House\",\"None\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Who is the main architect of The Rashtrapati Bhavan?\",\"answers\":[\"Sir Edwin Crowe\",\"Sir Edwin Lutyens\",\"Sir William Lutyens\",\"Sir Jeff Lutyens\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"How many rooms are there in The Rashtrapati Bhavan?\",\"answers\":[\"340\",\"240\",\"349\",\"129\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The presidential palace \\\"White House\\\" is located at __________\",\"answers\":[\"Flordia, United States\",\"California, United States\",\"Washington D.C., United States\",\"Lex D.C., United States\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Tajik presidential palace is also known as ___________\",\"answers\":[\"Palace of war\",\"Palace of Unity\",\"Palace of peace\",\"None of the above\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The most expensive presidential palace in the world is located in _________\",\"answers\":[\"Dubai\",\"United Arab Emirates\",\"Pakistan\",\"India\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Presidential palace of Poland is located in _____________\",\"answers\":[\"Warworld\",\"Waresee\",\"Warsaw\",\"Polymer\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which one of the following is the name of the palace in Rome?\",\"answers\":[\"Quinton Palace\",\"White house Building\",\"Athens Palace\",\"Quirinal Palace\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The official residence of the President of Hellenic is located in ________\",\"answers\":[\"Athens\",\"Rome\",\"Venezula\",\"Washington D.C.\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Dhaka is the capital of ______________.\",\"answers\":[\"China\",\"Bangladesh\",\"Turkey\",\"Pakistan\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Bangabhaban is located in ________\",\"answers\":[\"Mirpur\",\"Dhaka\",\"Chittagong\",\"None of the above\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"_______________ palace took 12 years to built.\",\"answers\":[\"The Rashtrapati bhavan\",\"The White House\",\"The Elysee Palace\",\"The Great Kremlin Palace\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The Great Kremblin Palace is located in _________\",\"answers\":[\"Moscow\",\"Flordia\",\"Saint Petersburg\",\"Kazan\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The Great Kremlin Palace belongs to which country\",\"answers\":[\"United States\",\"Great Britain\",\"Russia\",\"Bangladesh\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The Elysee palace is located in ___________.\",\"answers\":[\"Paris\",\"Rome\",\"Athen\",\"Jaipur\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Paris is the capital of __________.\",\"answers\":[\"Italy\",\"France\",\"South Africa\",\"Newzland\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The Bellevue Palace is located on the banks of _______ river\",\"answers\":[\"Nile\",\"Spree\",\"Beas\",\"Indus\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Berlin is the capital of ________\",\"answers\":[\"Germany\",\"Italy\",\"Greece\",\"Russia\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Name of the presidential palace in Germany?\",\"answers\":[\"President of Hellenic\",\"Bellevue Palace\",\"Quirinal Palace\",\"Bangabhaban\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The Tajik Presidential palace is located in _______\",\"answers\":[\"Agra\",\"Dushanbe\",\"Dushanik\",\"Tajhore\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which of the following is the oldest tennis tournament?\",\"answers\":[\"U.S Open\",\"Australian Open\",\"French Open\",\"Wimbeldon\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"International sporting event held for differently abled people.\",\"answers\":[\"Paralympic Games\",\"Paraolympic Games\",\"Olympic Games\",\"Snooker Games\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Brazil has won FIFA World Cup tournament ______ times\",\"answers\":[\"1\",\"6\",\"4\",\"5\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"International basket ball tournament\",\"answers\":[\"FICA\",\"FIBA\",\"FICA\",\"FIFA\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The trophy given to the winning team in FIBA is called ______\",\"answers\":[\"Naziermith\",\"Naismith\",\"Smith\",\"Blacksmith\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"International multi-sport event for athletes from the commonwealth nations\",\"answers\":[\"Olympic games\",\"Paralympic games\",\"Commonwealth games\",\"None of the above\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"FIBA Basketball world cup tournament first held in _____.\",\"answers\":[\"1947\",\"1950\",\"1859\",\"1900\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Wimbledon Championship first held in _______.\",\"answers\":[\"1929\",\"1877\",\"1977\",\"1777\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"FIFA World cup tournament first held in _______.\",\"answers\":[\"1930\",\"1929\",\"1939\",\"1938\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The 2010 Commonwealth games were held in ______.\",\"answers\":[\"Jaipur\",\"New Delhi\",\"Chennai\",\"Cochin\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Summer Paralympics 2012 were held in ______.\",\"answers\":[\"London\",\"New Delhi\",\"Washington D.C.\",\"Paris\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"_______ won the 2014 FIFA world cup.\",\"answers\":[\"United States\",\"Germany\",\"France\",\"Brazil\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"_______ won the 2014 FIBA cup.\",\"answers\":[\"United States\",\"China\",\"India\",\"Japan\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The Women's singles title was won by ______ in the 2015 Wimbledon Championship.\",\"answers\":[\"Venus Williams\",\"Serena Williams\",\"Steffi Graff\",\"Martina Hingis\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which one of these is a great Indian epic?\",\"answers\":[\"Bahubali\",\"Mahabharata\",\"Krishna\",\"Gita\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Who is in the chariot driven by Lord Krishna?\",\"answers\":[\"Bhima\",\"Arjuna\",\"Nakula\",\"Sahadeva\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"What is the name of the baby born with Kavach and Kundal in the basket?\",\"answers\":[\"Karna\",\"Arjuna\",\"Krishna\",\"Nakula\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Who found Sita in the Ashokavana?\",\"answers\":[\"Hanuman\",\"Rama\",\"Lakshmana\",\"Ravana\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The king with 10 heads\",\"answers\":[\"Rama\",\"Ravana\",\"Krishna\",\"Arjuna\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Who is lying in the bed of arrows?\",\"answers\":[\"Dronacharya\",\"Bhishma\",\"Karna\",\"Bhima\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Where did the fight between Rama and Ravana take place?\",\"answers\":[\"Lanka\",\"Ayodhya\",\"Kurukshetra\",\"Chennai\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which one of the following fort built by Emperor Akbar?\",\"answers\":[\"Taj Mahal\",\"Golconda Fort\",\"Agra Fort\",\"Red Fort\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Which one of the following is the oldest fort in India?\",\"answers\":[\"Jaisalmer Fort\",\"Amber Fort\",\"Agra Fort\",\"Red Fort\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Jaisalmer Fort is situated in _____.\",\"answers\":[\"Thar desert\",\"Sahara desert\",\"Green desert\",\"Amazon desert\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The _______ fort is situated in Hyderabad. \",\"answers\":[\"Amber fort\",\"Agra fort\",\"Golconda fort\",\"Srirangapatnam fort\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"The _______ fort is situated in Mysore.\",\"answers\":[\"Amber fort\",\"Agra fort\",\"Golconda fort\",\"Srirangapatnam fort\"],\"ansIndex\":\"4\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Red fort is also known as \",\"answers\":[\"Yamuna fort\",\"Lal Qila\",\"Lal badh\",\"Lal badhur\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Red fort is built on the banks of _____ river.\",\"answers\":[\"Indus\",\"Yamuna\",\"Ganga\",\"Amaravathi\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Red fort was built by _______\",\"answers\":[\"Akbar\",\"Shah Jahan\",\"Raja Raja cholzan\",\"Arungazeb\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Tipu Sultan lived in this fort.\",\"answers\":[\"Mysore fort\",\"Srirangapatnam fort\",\"Agra fort\",\"Vellore Fort\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Amber fort was built by _______.\",\"answers\":[\"Raja Man Singh\",\"Manmohan Singh\",\"Raj Kulber Singh\",\"Kushwant Singh\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Amber fort is in ______.\",\"answers\":[\"Agra\",\"Jaipur\",\"Ranchi\",\"Patna\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Amber fort is built of ___________ and __________.\",\"answers\":[\"Green stone and White marbles\",\"Orange stone and White marbles\",\"White marbles and Red sand stones\",\"Granite and White marbles\"],\"ansIndex\":\"3\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Golconda fort is build on a _____ hill.\",\"answers\":[\"Red sand stone\",\"Granite\",\"Clay\",\"Tiles\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Fear of thunder and lightning.\",\"answers\":[\"Claustrophobia\",\"Astraphobia\",\"Nyctophobia\",\"Acrophobia\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Fear of heights.\",\"answers\":[\"Acrophobia\",\"Astraphobia\",\"Arachnophobia\",\"Nyctophobia\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Fear of spiders.\",\"answers\":[\"Claustrophobia\",\"Arachnophobia\",\"Nyctophobia\",\"Astraphobia\"],\"ansIndex\":\"2\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Fear of the dark.\",\"answers\":[\"Nyctophobia\",\"Arachnophobia\",\"Astraphobia\",\"Acrophobia\"],\"ansIndex\":\"1\"},{\"subject\":\"GK\",\"category\":\"Book4\",\"subcategory\":\"General\",\"ask\":\"Fear of closed places like store rooms, elevators or bathrooms.\",\"answers\":[\"Claustrophobia\",\"Nyctophobia\",\"Astraphobia\",\"Acrophobia\"],\"ansIndex\":\"1\"}]";

    public static final String TAG = "droid";
    public static final String AUTHORITY = "com.droid.quizmaster";

    private static class Question {
        public String question;
        public String[] answerOptions;
        public int answerIndex;
        public String subject;
        public String category;
        public String subcategory;

        public Question(String paramSubject, String paramCategory, String paramSubCategory,
                              String paramName, String[] paramOptions, int paramAnswerIndex)
        {
            this.subject = paramSubject;
            this.category = paramCategory;
            this.subcategory = paramSubCategory;
            this.question = paramName;
            this.answerOptions = paramOptions;
            this.answerIndex = paramAnswerIndex;
        }
    }

    private static class QuestionPapers {
        public int FileId;
        public String QuestionPaperName;
        public Question[] QuestionsToAsk;

        public QuestionPapers(int paramFileId, String paramQuestionName)
        {
            this.FileId = paramFileId;
            this.QuestionPaperName = paramQuestionName;
        }
    }

    static ArrayList<QuestionPapers> QuestionsDB = new ArrayList<QuestionPapers>();

    {
        QuestionsDB.add(new QuestionPapers(1, "General Knowledge"));
        QuestionsDB.add(new QuestionPapers(2, "English Grammar"));

        QuestionsDB.get(0).QuestionsToAsk = GetQuizQuestions(generalKnowledge);
        QuestionsDB.get(1).QuestionsToAsk = GetQuizQuestions(englishGrammar);

    }

    public static Question[] GetQuizQuestions(String quizQuestions)
    {
        Question[] question = null;
        try {

            Log.d("GetQuizQuestions","Questions");

            JSONArray quizArray = new JSONArray(quizQuestions);

            question = new Question[quizArray.length()];

            for (int iIndex = 0; iIndex < quizArray.length(); iIndex++) {

                JSONObject _jsonObject = quizArray.getJSONObject(iIndex);

                String _subject = _jsonObject.getString("subject");
                String _category = _jsonObject.getString("category");
                String _subCategory = _jsonObject.getString("subcategory");
                String _ask = _jsonObject.getString("ask");
                Integer _askIndex = Integer.valueOf(_jsonObject.getString("ansIndex"));
                JSONArray _jsonArrayAnswers = _jsonObject.getJSONArray("answers");

                String[] _answers = new String[_jsonArrayAnswers.length()];

                for (int iAnswerIndex = 0; iAnswerIndex < _jsonArrayAnswers.length();iAnswerIndex++) {
                    _answers[iAnswerIndex] = _jsonArrayAnswers.getString(iAnswerIndex);
                }
                question[iIndex] = new Question(
                        _subject, _category, _subCategory,
                        _ask, _answers, _askIndex);
            }
        }
        catch(JSONException e)
        {
            Log.d("read",e.getMessage());
            e.printStackTrace();
        }
        return question;
    }

    public static final class Quiz {

        public static class QuizContent {
            public static final String CONTENT_TYPE =
                    "vnd.android.cursor.dir" + "/" + AUTHORITY + "." + QuizColumns.QUESTION;

            public static final String ENTRY_CONTENT_TYPE =
                    "vnd.android.cursor.item" + "/" + AUTHORITY + "." + QuizColumns.QUESTION;

            public static final String DEFAULT_SORT_ORDER = QuizColumns.QUESTION;

            public static final Uri CONTENT_URI;

            static {
                CONTENT_URI = Uri
                        .parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + QuizColumns.QUESTION);
            }
        }


        public static interface QuizColumns  extends BaseColumns {
            public static final String QUESTION = "question";
            public static final String ANSWEROPTIONS = "answerOptions";
            public static final String ANSWERINDEX = "answerIndex";
            public static final String SUBJECT = "subject";
            public static final String CATEGORY = "category";
            public static final String SUBCATEGORY = "subcategory";
        }

    }

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int MATCH_ACCOUNT = 1;
    private static final int MATCH_ACCOUNT_ID = 2;
    private static final int MATCH_ACCOUNT_NAME = 3;
    private static final int MATCH_ACCOUNT_DOLLARS = 4;

    static {
        sUriMatcher.addURI(AUTHORITY, Quiz.QuizColumns.co, MATCH_ACCOUNT);
        sUriMatcher.addURI(AUTHORITY, Quiz.QuizColumns.ACCOUNT + "/#", MATCH_ACCOUNT_ID);
        sUriMatcher.addURI(AUTHORITY, Quiz.QuizColumns.ACCOUNT + "/#/" + Words.AccountColumns.NAME, MATCH_ACCOUNT_NAME);
        sUriMatcher.addURI(AUTHORITY, Quiz.QuizColumns.ACCOUNT + "/#/" + Words.AccountColumns.DOLLARS, MATCH_ACCOUNT_DOLLARS);
    }
}
