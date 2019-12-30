package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();

        List<Category> categories = this.categoryRepository.findAllByItemsCount();

        for (Category category : categories) {
            sb.append(String.format("Category: %s\n", category.getName()));
            for (Item item : category.getItemList()) {
                sb.append(String.format( "--- Item Name: %s\n" +
                        "--- Item Price: %s\n" +
                        "\n", item.getName(), item.getPrice()));
            }

        }
        return sb.toString().trim();
    }
}
