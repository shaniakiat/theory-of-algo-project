import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.io.IOException;
import javafx.geometry.Point2D;

// author: Shania Kiat

public class closestPairPoints {
    public static void main(String[] args) throws IOException {

        List<Point2D> p = new ArrayList<>();

        // generate 10,000 random points
        // for (int i = 0; i < 10000; i++) {
        // double x = Math.random() + 10;
        // double y = Math.random() + 10;
        // Point2D tempXY = new Point2D(x, y);
        // p.add(tempXY);
        // }

        // these are the points from
        // https://www.geeksforgeeks.org/closest-pair-of-points-using-divide-and-conquer-algorithm/
        Point2D p0 = new Point2D(2, 3);
        Point2D p1 = new Point2D(12, 30);
        Point2D p2 = new Point2D(40, 50);
        Point2D p3 = new Point2D(5, 1);
        Point2D p4 = new Point2D(12, 10);
        Point2D p5 = new Point2D(3, 4);
        p.add(p0);
        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);

        // // Open the file
        // FileInputStream fstream = new FileInputStream("pointsTWO.txt");
        // BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        // String strLine;
        // // Read File Line By Line
        // while ((strLine = br.readLine()) != null) {
        // // Print the content on the console
        // // System.out.println(strLine);
        // // for (int i = 0; i < 1000; i++) {
        // String s = strLine;
        // String[] splited = s.split("\\s+");
        // double x = Double.parseDouble(splited[0]);
        // double y = Double.parseDouble(splited[1]);
        // // System.out.println(x + " " + y);
        // Point2D pts = new Point2D(x, y);
        // p.add(pts);
        // // }
        // }
        // // Close the input stream
        // fstream.close();
        // // System.out.println(p.toString());
        System.out.println(closestPair(p));
    }

    // source code from
    // https://www.tutorialspoint.com/Closest-Pair-of-Points-Problem
    public static double closestPair(List<Point2D> p) {
        List<Point2D> xSorted = new ArrayList<>(p);
        List<Point2D> ySorted = new ArrayList<>(p);

        // Sort the points by X values
        // from David Luong
        Collections.sort(xSorted, new Comparator<Point2D>() {
            public int compare(Point2D point1, Point2D point2) {
                int result = Double.compare(point1.getX(), point2.getX());
                if (result == 0) {
                    // both X are equal -> compare Y too
                    result = Double.compare(point1.getY(), point2.getY());
                }
                return result;
            }
        });

        // Sort the points by Y values
        // from David Luong
        Collections.sort(ySorted, new Comparator<Point2D>() {
            public int compare(Point2D point1, Point2D point2) {
                int result = Double.compare(point1.getY(), point2.getY());
                if (result == 0) {
                    // both X are equal -> compare Y too
                    result = Double.compare(point1.getX(), point2.getX());
                }
                return result;
            }
        });
        // to debug
        // System.out.println("xSorted: " + xSorted.toString());
        // System.out.println("ySorted: " + ySorted.toString());

        // set the distance as large as possible
        double d = Double.MAX_VALUE;
        int n = p.size();

        // just do brute force if there are only 3 points or less
        if (n <= 3) {
            // System.out.println("base case");

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < 3; j++) {
                    double distance = p.get(i).distance(p.get(j));
                    d = Math.min(d, distance);
                }
            }
            return d;
        }

        // Compute separation line L such that half the points are on one side and half
        // on the other side.
        int mid = n / 2;
        Point2D midP = xSorted.get(mid);

        List<Point2D> left = ySorted.subList(0, mid);
        List<Point2D> right = ySorted.subList(mid, n);
        // Recursion part
        double d1 = closestPair(left);
        double d2 = closestPair(right);
        d = Math.min(d1, d2);

        // delete all points further than d from seperation line
        List<Point2D> strip = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(ySorted.get(i).getX() - midP.getX()) < d) {
                strip.add(ySorted.get(i));
                k++;
            }
        }
        // System.out.println(k);

        // Sort remaining points by y-coordinate.
        Collections.sort(ySorted, new Comparator<Point2D>() {
            public int compare(Point2D point1, Point2D point2) {
                int result = Double.compare(point1.getY(), point2.getY());
                if (result == 0) {
                    // both Y are equal -> compare X too
                    result = Double.compare(point1.getX(), point2.getX());
                }
                return result;
            }
        });

        // Scan points in strip and
        // compare distance between two points in the strips
        // If any of these distances is less than d,
        // update d.
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k && (strip.get(j).getY() - strip.get(i).getY()) < d; j++) {
                if (strip.get(i).distance(strip.get(j)) < d) {
                    d = strip.get(i).distance(strip.get(j));
                }
            }
        }
        return d;
    }
}