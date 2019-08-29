import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.*;

public class RouteCalculatorTest extends TestCase {

   RouteCalculator testCalculator;
   StationIndex index;

   @Override
   protected void setUp() {

      index = new StationIndex();

//           TEST METRO SCHEME
//
//                      [b1]
//    [r1]              [b2]
//    [r2/g1] - [g2] - [b3/g3] - [g4]
//    [r3]

      Line line1 = new Line(1, "Red");
      Line line2 = new Line(2,"Green");
      Line line3 = new Line(3, "Blue");

      List<Station> stations = new ArrayList<>();

      Station r1 = new Station("red-1", line1);
      Station r2 = new Station("red-2", line1);
      Station r3 = new Station("red-3", line1);
      line1.addStation(r1);
      line1.addStation(r2);
      line1.addStation(r3);


      Station g1 = new Station("green-1", line2);
      Station g2 = new Station("green-2", line2);
      Station g3 = new Station("green-3", line2);
      Station g4 = new Station("green-4", line2);
      line2.addStation(g1);
      line2.addStation(g2);
      line2.addStation(g3);
      line2.addStation(g4);

      Station b1 = new Station("blue-1", line3);
      Station b2 = new Station("blue-2", line3);
      Station b3 = new Station("blue-3", line3);
      line3.addStation(b1);
      line3.addStation(b2);
      line3.addStation(b3);

      stations.add(r1);
      stations.add(r2);
      stations.add(r3);
      stations.add(g1);
      stations.add(g2);
      stations.add(g3);
      stations.add(g4);
      stations.add(b1);
      stations.add(b2);
      stations.add(b3);

      index.addLine(line1);
      index.addLine(line2);
      index.addLine(line3);

      index.stations.addAll(stations);

      TreeSet<Station> r2_g1 = new TreeSet<Station>();
      r2_g1.add(g1);
      TreeSet<Station> g1_r2 = new TreeSet<Station>();
      g1_r2.add(r2);
      TreeSet<Station> b3_g3 = new TreeSet<Station>();
      b3_g3.add(g3);
      TreeSet<Station> g3_b3 = new TreeSet<Station>();
      g3_b3.add(b3);

      index.connections.put(r2, r2_g1);
      index.connections.put(g2, g1_r2);
      index.connections.put(b3, b3_g3);
      index.connections.put(g3, g3_b3);

      testCalculator = new RouteCalculator(index);
   }

   public void testGetShortestRoute() {

      List<Station> actual = testCalculator.getShortestRoute(index.getStation("red-2"), index.getStation("blue-2"));
      List<Station> expected = new ArrayList<Station>();
      expected.add(index.getStation("red-2"));
      expected.add(index.getStation("green-1"));
      expected.add(index.getStation("green-2"));
      expected.add(index.getStation("green-3"));
      expected.add(index.getStation("blue-3"));
      expected.add(index.getStation("blue-2"));
      assertEquals(expected,actual);
   }

   public void testCalculateDuration() {
      double actual = RouteCalculator.calculateDuration(testCalculator.getShortestRoute(index.getStation("red-1"), index.getStation("green-2")));
      double expected = 8.5;
      assertEquals(expected, actual);
   }


}
