package Tests;

import DTO.AuthDTO;
import DTO.ProductCategoryDTO;
import Pages.ProductCategory;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Helpers.DBHelpers.confirmEmailCompanyById;
import static Pages.Company.createCompany;
import static Pages.Company.getDefaultParams;
import static Pages.ProductCategory.*;
import static org.junit.Assert.*;

public class ProductCategoryTest extends TestBase {

    @Test
    public void postProductCategory() {
        ProductCategoryDTO productCategoryDTO = createProductCategory();

        assertEquals("alcohol", productCategoryDTO.getName());
        assertEquals("18", productCategoryDTO.getAgeLimit().toString());
        assertTrue(productCategoryDTO.getId() > 0);
        assertTrue(productCategoryDTO.getImageId() > 0);
        assertTrue(productCategoryDTO.getImage().getId() > 0);
        assertNotNull(productCategoryDTO.getImage().getBytes());
    }

    @Test
    public void postProductCategory_2() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", "super_product");
        params.put("ageLimit", 6);
        File image = new File("src/test/java/Resources/vtb.jpg");

        ProductCategoryDTO productCategoryDTO = createProductCategory(params, image);

        assertEquals("super_product", productCategoryDTO.getName());
        assertEquals("6", productCategoryDTO.getAgeLimit().toString());
        assertTrue(productCategoryDTO.getId() > 0);
        assertTrue(productCategoryDTO.getImageId() > 0);
        assertTrue(productCategoryDTO.getImage().getId() > 0);
        assertNotNull(productCategoryDTO.getImage().getBytes());
    }

    @Test
    public void getProductCategoryAll() {
        ArrayList list = ProductCategory.getListProductCategory(getAllProductCategory());
        assertTrue(list.size() > 0);

        for (Object o : list) {
            ProductCategoryDTO dto = (ProductCategoryDTO) o;
            if (dto.getName().equals("alcohol")) {
                assertEquals("18", dto.getAgeLimit().toString());
                assertTrue(dto.getId() > 0);
                assertTrue(dto.getImageId() > 0);
                assertTrue(dto.getImageId() > 0);
            }
        }
    }

    @Test
    public void getProductCategoryById() {
        String name = "new_product";
        int age = 8;
        File image = new File("src/test/java/Resources/vtb.jpg");

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("ageLimit", age);

        ProductCategoryDTO create = createProductCategory(params, image);

        ProductCategoryDTO get = ProductCategory
                .getProductCategoryById(create.getId());

        assertEquals(name, get.getName());
        assertEquals("8", get.getAgeLimit().toString());
        assertTrue(get.getId() > 0);
        assertTrue(get.getImageId() > 0);
        assertTrue(get.getImageId() > 0);
    }

    @Test
    public void putProductCategoryById() {
        int categoryId = createProductCategory()
                .getId();

        Map companyParams = getDefaultParams();
        companyParams.put("productCategoryId", categoryId);

        int companyId = createCompany(companyParams)
                .getCompany()
                .getId();

        confirmEmailCompanyById(companyId);

        String name = "update_product";
        int age = 6;
        File image = new File("src/test/java/Resources/update-your-content.jpg");
        HashMap<String, Object> newParams = new HashMap<>();
        newParams.put("name", name);
        newParams.put("ageLimit", age);

        ProductCategoryDTO create = updateProductCategory(categoryId, newParams, image);

        ProductCategoryDTO get = ProductCategory
                .getProductCategoryById(create.getId());

        assertEquals(name, get.getName());
        assertEquals("6", get.getAgeLimit().toString());
        assertTrue(get.getId() > 0);
        assertTrue(get.getImageId() > 0);
        assertTrue(get.getImageId() > 0);
    }

    @Test
    public void getProductCategoryImageById() {
        int categoryId = createProductCategory().getId();

        String imageBytes = ProductCategory.getProductCategoryImageById(categoryId);
        assertTrue(imageBytes.contains("JPEG"));
    }

    @Test
    public void putProductCategoryImageById() {
        int categoryId = createProductCategory().getId();

        int imageId = ProductCategory.getProductCategoryById(categoryId)
                .getImageId();

        File newImage = new File("src/test/java/Resources/Malen.jpeg");
        ProductCategory.putProductCategoryImageById(categoryId, newImage);

        int newImageId = ProductCategory.getProductCategoryById(categoryId)
                .getImageId();

        assertNotEquals(imageId, newImageId);
    }
}
