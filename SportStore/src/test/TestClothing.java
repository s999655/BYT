package test;

import main.product.Brand;
import main.product.Clothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestClothing {

    private static final String TEST_FILE = "test_clothing.xml";

    @BeforeEach
    @AfterEach 
    void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }


    @Test
    void constructor_createsClothingAndAddsToExtent() {
        String[] materials = {"cotton", "polyester"};

        Clothing c = new Clothing(
                1,
                new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)),
                 "T-Shirt",
                25.0,
                10,
                "TS-01",
                "Blue",
                10.0,
                "Shelf1",
                materials,
                Clothing.Size.M,
                Clothing.Category.men
        );

        assertNotNull(c);
        assertEquals(25.0, c.getPrice());
        assertEquals(10, c.getStockQuantity());

        // check extent
        assertTrue(Clothing.getExtent().contains(c));
    }

    @Test
    void setSize_null_throwsException() {
        String[] materials = {"cotton"};

        Clothing c = new Clothing(
                2,
                new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)),
                "Shirt",
                30.0,
                5,
                "S-01",
                "White",
                15.0,
                "Shelf2",
                materials,
                Clothing.Size.L,
                Clothing.Category.women
        );

        Exception e = assertThrows(IllegalArgumentException.class, () -> c.setSize(null));
        assertEquals("size cannot be null", e.getMessage());
    }

    @Test
    void setMaterial_emptyArray_throwsException() {
        String[] emptyMaterials = {};

        Exception e = assertThrows(IllegalArgumentException.class, () -> new Clothing(
                3,
                new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)),
                "Pants",
                40.0,
                8,
                "P-01",
                "Black",
                20.0,
                "Shelf3",
                emptyMaterials,
                Clothing.Size.S,
                Clothing.Category.kids
        ));

        assertEquals("material cannot be empty", e.getMessage());
    }

    @Test
    void setCategory_null_throwsException() {
        String[] materials = {"wool"};

        Exception e = assertThrows(IllegalArgumentException.class, () -> new Clothing(
                4,
                new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)),
                "Jacket",
                60.0,
                3,
                "J-01",
                "Green",
                30.0,
                "Shelf4",
                materials,
                Clothing.Size.XL,
                null
        ));

        assertEquals("category cannot be null", e.getMessage());
    }

    @Test
    void extentPersistence_saveAndLoadRestoresClothing() {
        String[] materials1 = {"cotton"};
        String[] materials2 = {"wool"};

        // Clear extent before test
        Clothing.loadExtent("non_existing_file.xml"); // resets extent

        Clothing c1 = new Clothing(10, new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)), "Shirt1", 20.0, 5, "S1", "Red", 10.0, "A1", materials1, Clothing.Size.M, Clothing.Category.men);
        Clothing c2 = new Clothing(11, new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)), "Jacket1", 50.0, 2, "J1", "Black", 30.0, "B2", materials2, Clothing.Size.L, Clothing.Category.women);

        Clothing.saveExtent(TEST_FILE);

        // reset extent and load from file
        Clothing.loadExtent(TEST_FILE);
        var extent = Clothing.getExtent();

        assertEquals(2, extent.size());
        assertEquals(10, extent.get(0).getProductID());
        assertEquals(11, extent.get(1).getProductID());

        // clean up test file
        new File(TEST_FILE).delete();
    }
}
