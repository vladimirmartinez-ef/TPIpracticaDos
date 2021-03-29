package container;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import junit.framework.TestCase;

public class WarehouseTest extends TestCase {

    private Warehouse Warehouse = null;
    private int num_Packages_To_Test = 5;
    private int Warehouse_Volume = num_Packages_To_Test - 1;
    private Package[] b = null;
    private double package_Unit_Volume = 10.0;

    @Override
    protected void setUp() throws Exception {
        assertTrue("Test case error, you must test at least 1 Package", num_Packages_To_Test > 0);
        assertTrue("This test case is set up assuming that the Warehouse cannot contain all the Packages, please check and change parameters", num_Packages_To_Test > Warehouse_Volume);

        double Warehouse_capacity = 0;
        // We create the Packages that we need.
        b = new Package[num_Packages_To_Test];
        for (int i = 0; i < num_Packages_To_Test; i++) {
            if (i < Warehouse_Volume) {
                Warehouse_capacity += (i + 1) * package_Unit_Volume;
            }
            b[i] = new Package((i + 1) * package_Unit_Volume);
        }
        // Now, we create the Warehouse once we figure out how big a Warehouse we
        // need.
        Warehouse = new Warehouse(Warehouse_capacity);
    }

    /**
     * Test to check that Warehouse.add(Package) is implemented correctly
     */
    public void testAdd() {
        Warehouse.clear();
        for (int i = 0; i < Warehouse_Volume; i++) {
            assertTrue("Warehouse.add(Package) failed to add a new Package!", Warehouse.add(b[i]));
            assertFalse("Warehouse.add(Package) seems to allow the same Package to be added twice!", Warehouse.add(b[i]));
            assertTrue("Warehouse does not contain a Package after it is supposed to have been added!", Warehouse.contains(b[i]));
        }
        for (int i = Warehouse_Volume; i < num_Packages_To_Test; i++) {
            assertFalse("Warehouse.add(Package) allows a Package to be added even though it is already full!", Warehouse.add(b[i]));
        }
    }

    /**
     * Test to check that Warehouse.getPackages() is implemented correctly
     */
    public void testGetPackages() {
        Random rnd = new Random();
        Warehouse.clear();

        // We put all the Packages into a list.
        LinkedList<Package> list = new LinkedList<Package>();
        for (int i = 0; i < Warehouse_Volume; i++) {
            list.add(b[i]);
        }
        // First we add the Packages to the Warehouse in some random order.
        for (int i = 0; i < Warehouse_Volume; i++) {
            Warehouse.add(list.remove(rnd.nextInt(list.size())));
        }
        // Next we call the iterator and check that the Packages come out in the correct order
        
 Iterator<Package> it = Warehouse.getPackages();
        int count = 0;
        while (it.hasNext() && count < Warehouse_Volume) {
            Package Package = it.next();
            assertTrue("Packages are not returned by Warehouse.getPackages() iterator in the correct order", b[count] == Package);
            if (b[count] != Package) {
                break;
            }
            count++;
        }
        assertEquals("Warehouse.getPackages() did not return all the Packages", Warehouse_Volume, count);
    }
}
