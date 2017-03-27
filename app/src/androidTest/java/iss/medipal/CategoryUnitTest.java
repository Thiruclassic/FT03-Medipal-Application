package iss.medipal;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import iss.medipal.dao.CategoryDao;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.model.Category;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Thirumal on 27/3/2017.
 */

public class CategoryUnitTest extends TestCase implements MedipalUnitTest {

    Category category;
    CategoryDao categoryDao;
    List<Category> categoryList;


    @Before
    public void setUp()
    {
        category = new Category();
        categoryDao = new CategoryDaoImpl(MedipalUnitTest.context);
        categoryList= categoryDao.getAllCategories();
    }

    @After
    public void close(){
        categoryDao.close();
    }

    @Test
    public void testAddCategory()
    {
        Category category=new Category();
        assertEquals("Category should not be added",-1,categoryDao.addCategory(category));


        category=categoryList.get(0);
        assertEquals("Category should retrieve correct date","SUP",category.getCode());

    }

    @Test
    public void testListSize()
    {
        List<Category> categories=categoryDao.getAllCategories();
        assertEquals("List should be the same",categoryList,categories);
    }

}
