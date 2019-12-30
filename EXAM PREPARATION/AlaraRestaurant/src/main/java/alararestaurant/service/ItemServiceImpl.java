package alararestaurant.service;

import alararestaurant.domain.dtos.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {

    private final static String ITEMS_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\items.json";

    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, FileUtil fileUtil, ModelMapper modelMapper, CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        StringBuilder sb = new StringBuilder();

        ItemImportDto[] itemImportDtos = this.gson.fromJson(items, ItemImportDto[].class);


        for (ItemImportDto itemImportDto : itemImportDtos) {
            Item item = this.modelMapper.map(itemImportDto, Item.class);
            if (!this.validationUtil.isValid(item) || this.itemRepository.findByName(item.getName()) != null){
                sb.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }

            Category category = this.categoryRepository.findByName(itemImportDto.getCategory());

            if (category == null){
                category = new Category();
                category.setName(itemImportDto.getCategory());
                if (!this.validationUtil.isValid(category)){
                    sb.append("Invalid data format.").append(System.lineSeparator());
                    continue ;
                }
                category = this.categoryRepository.saveAndFlush(category);
            }

            item.setCategory(category);

            this.itemRepository.saveAndFlush(item);
            sb.append(String.format("Record %s successfully imported.", item.getName()));
        }

        return sb.toString().trim();
    }
}
