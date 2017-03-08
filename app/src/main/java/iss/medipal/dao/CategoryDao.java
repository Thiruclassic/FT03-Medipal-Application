package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Category;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface CategoryDao {

    public Category getCategoryByCode(String code);
    public Category getCategoryById(int id);
    public Category getCategoryByName(String categoryName);

    public List<String> getAllCategories();
}
