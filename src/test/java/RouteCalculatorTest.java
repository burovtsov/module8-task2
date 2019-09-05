import core.Line;
import core.Station;
import junit.framework.TestCase;


import java.lang.reflect.Field;
import java.util.*;

public class RouteCalculatorTest extends TestCase {

   RouteCalculator testCalculator;
   StationIndex index;

   @Override
   protected void setUp() {
      testCalculator = Main.getRouteCalculator();

      Main main = new Main();
      Class mainClass = main.getClass();
      try {
        Field fieldStationIndex = mainClass.getDeclaredField("stationIndex");
        fieldStationIndex.setAccessible(true);
        index = (StationIndex) fieldStationIndex.get(main);
      }
      catch (NoSuchFieldException | IllegalAccessException e) {
         e.printStackTrace();
      }
   }

   public void testGetShortestRoute_NoConnections() {

      List<Station> actual = testCalculator.getShortestRoute(index.getStation("Улица Дыбенко"), index.getStation("Новочеркасская"));
      List<Station> expected = new ArrayList<>();
      expected.add(index.getStation("Улица Дыбенко"));
      expected.add(index.getStation("Проспект Большевиков"));
      expected.add(index.getStation("Ладожская"));
      expected.add(index.getStation("Новочеркасская"));

      assertEquals(expected,actual);
   }

   public void testGetShortestRoute_OneConnection() {

      List<Station> actual = testCalculator.getShortestRoute(index.getStation("Невский проспект"), index.getStation("Василеостровская"));
      List<Station> expected = new ArrayList<>();
      expected.add(index.getStation("Невский проспект"));
      expected.add(index.getStation("Гостиный двор"));
      expected.add(index.getStation("Василеостровская"));

      assertEquals(expected,actual);
   }

   public void testGetShortestRoute_TwoConnections() {

      List<Station> actual = testCalculator.getShortestRoute(index.getStation("Василеостровская"), index.getStation("Спасская"));
      List<Station> expected = new ArrayList<>();
      expected.add(index.getStation("Василеостровская"));
      expected.add(index.getStation("Гостиный двор"));
      expected.add(index.getStation("Невский проспект"));
      expected.add(index.getStation("Сенная площадь"));
      expected.add(index.getStation("Спасская"));

      assertEquals(expected,actual);
   }

   public void testCalculateDuration_noConnections() {
      double actual = RouteCalculator.calculateDuration(testCalculator.getShortestRoute(index.getStation("Приморская"), index.getStation("Елизаровская")));
      double expected = 12.5;
      assertEquals(expected, actual);
   }

   public void testCalculateDuration_withConnections() {
      double actual = RouteCalculator.calculateDuration(testCalculator.getShortestRoute(index.getStation("Фрунзенская"), index.getStation("Чкаловская")));
      double expected = 16;
      assertEquals(expected, actual);
   }


}
