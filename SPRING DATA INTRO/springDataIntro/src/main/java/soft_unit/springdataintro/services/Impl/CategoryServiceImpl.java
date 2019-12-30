package soft_unit.springdataintro.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_unit.springdataintro.entities.Category;
import soft_unit.springdataintro.repositories.CategoryRepository;
import soft_unit.springdataintro.services.CategoryService;
import soft_unit.springdataintro.utils.FileUtil;

import java.io.*;

import static soft_unit.springdataintro.constants.Constant.CATEGORIES_FILE_URL;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {
        if (this.categoryRepository.count() != 0) {
            return;
        }

        String[] categoryName = this.fileUtil.fileText(CATEGORIES_FILE_URL);

        for (String name : categoryName) {
            Category category = new Category();
            category.setName(name);
            this.categoryRepository.saveAndFlush(category);
        }
    }
}
