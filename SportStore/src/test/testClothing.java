package test;

import main.product.Clothing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class testClothing {

    @Test
    void constructor_createsClothingCorrectly() {
        String[] materials = {"cotton", "polyester"};

        Clothing c = new Clothing(
                1,
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
    }

    @Test
    void setSize_null_throwsException() {
        String[] materials = {"cotton"};

        Clothing c = new Clothing(
                2,
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
}
