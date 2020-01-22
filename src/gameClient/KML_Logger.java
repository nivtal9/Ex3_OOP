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
        str = new StringBuffer();
        KML_Start();
    }
    /**
     * this function initialize the working platform to KML
     */
    private void KML_Start(){
        str.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
                        "  <Document>\r\n" +
                        "    <name>" + "Game stage :"+level + "</name>" +"\r\n"
        );
        KML_node();
    }
    /**
     * this function initialize the node icon to KML
     */
    private void KML_node(){
        str.append(" <Style id=\"node\">\r\n" +
                "      <IconStyle>\r\n" +
                "        <Icon>\r\n" +
                "          <href>http://maps.google.com/mapfiles/kml/shapes/donut.png</href>\r\n" +
                "        </Icon>\r\n" +
                "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                "      </IconStyle>\r\n" +
                "    </Style>"
        );
        KML_Fruit();
    }

    /**
     * this function initialize the Fruits icon to KML (Type 1 and -1)
     */
    private void KML_Fruit(){
        str.append(
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
                        "    </Style>"
        );
        KML_Robot();
    }
    /**
     * this function initialize the Robots icon to KML
     */
    private void KML_Robot(){
        str.append(
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
        LocalDateTime Present_time = LocalDateTime.now();
        str.append(
                "    <Placemark>\r\n" +
                        "      <TimeStamp>\r\n" +
                        "        <when>" + Present_time.minusHours(2)+ "</when>\r\n" +
                        "      </TimeStamp>\r\n" +
                        "      <styleUrl>#" + id + "</styleUrl>\r\n" +
                        "      <Point>\r\n" +
                        "        <coordinates>" + location + "</coordinates>\r\n" +
                        "      </Point>\r\n" +
                        "    </Placemark>\r\n"
        );
    }
    void Place_Mark_edge(String locationfrom,String locationto)
    {
        str.append(
                "        <Placemark>\n" +
                        "      <LineString>\n" +
                        "        <extrude>1</extrude>\n" +
                        "        <tessellate>1</tessellate>\n" +
                        "        <coordinates>"+locationfrom+" "+locationto+"</coordinates>\n" +
                        "      </LineString>\n" +
                        "    </Placemark> "
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
