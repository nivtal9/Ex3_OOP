package gameClient;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * this class responsible to create 24 kml files
 * the file can be loaded to google earth
 * and view the game course in a specific level
 * the kml relevant only for auto game.
 */
class KML_Logger {
    /**
     * private data types of the class
     * int level
     * StringBuffer str- the string will be written there.
     */
    private int level;
    private StringBuffer str;

    /**
     * simple constructor
     * @param level
     */
    KML_Logger(int level) {
        this.level = level;
        this.str = new StringBuffer();
        KML_Play();
    }

    /**
     * this function initialize the working platform
     * attribute icon to node
     * attribute icon to robot
     * attribute icon to fruit
     * the icon for fruit associated to type -1 (purple) will be different from fruit associated to type 1 (red)
     */
    private void KML_Play()
    {
        str.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
                        "  <Document>\r\n" +
                        "    <name>" + "Game stage :"+level + "</name>" +"\r\n"+
                        " <Style id=\"node\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/pal3/icon35.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit_-1\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit_1\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/paddle/red-stars.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"robot\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/shapes/motorcycling.png></href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>"
        );
    }

    /**
     * this function is used in "paint"
     * after painting each element
     * the function enters the kml the location of each element
     * @param id
     * @param location
     */
    void Place_Mark(String id, String location)
    {
        LocalDateTime time = LocalDateTime.now();
        str.append(
                "    <Placemark>\r\n" +
                        "      <TimeStamp>\r\n" +
                        "        <when>" + time+ "</when>\r\n" +
                        "      </TimeStamp>\r\n" +
                        "      <styleUrl>#" + id + "</styleUrl>\r\n" +
                        "      <Point>\r\n" +
                        "        <coordinates>" + location + "</coordinates>\r\n" +
                        "      </Point>\r\n" +
                        "    </Placemark>\r\n"
        );
    }

    /**
     * mark the kml the end of the script
     */
    void KML_Stop()
    {
        str.append("  \r\n</Document>\r\n" +
                "</kml>");
        SaveFile();
    }

    /**
     * save the kml string to a file
     */
    private void SaveFile(){
        try
        {
            File file=new File("data/"+level+".kml");
            PrintWriter pw=new PrintWriter(file);
            pw.write(str.toString());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
