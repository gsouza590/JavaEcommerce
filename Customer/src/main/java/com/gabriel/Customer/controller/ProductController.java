package com.gabriel.Customer.controller;

import com.gabriel.Backend.dto.CategoryDto;
import com.gabriel.Backend.dto.ProductDto;
import com.gabriel.Backend.model.Category;
import com.gabriel.Backend.service.CategoryService;
import com.gabriel.Backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("page", "`Produtos`");
        model.addAttribute("title", "Produtos");
        List<Category> categories = categoryService.findAllByActivated();
        List<ProductDto> products = productService.products();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/product-detail/{id}")
    public  String details(@PathVariable ("id")Long id, Model model){
        ProductDto product = productService.getById(id);
        List<ProductDto>productDtoList= productService.findAllByCategory(product.getCategory().getName());
        model.addAttribute("products", productDtoList);
        model.addAttribute("title","Detalhes do Produto");
        model.addAttribute("page","Detalhes do Produto");
        model.addAttribute("productDetail", product);
        return "product-detail";

    }


    @GetMapping("/shop-detail")
    public String shopDetail(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.randomProduct();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews", listView);
        model.addAttribute("title", "Produtos");
        model.addAttribute("page", "Produtos");
        model.addAttribute("products", products);
        return "shop-detail";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.filterHighProducts();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("title", "Produtos");
        model.addAttribute("page", "Produtos");
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        return "shop-detail";
    }


    @GetMapping("/lower-price")
    public String filterLowerPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.filterLowerProducts();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews", listView);
        model.addAttribute("title", "Produtos");
        model.addAttribute("page", "Produtos");
        model.addAttribute("products", products);
        return "shop-detail";
    }
    @GetMapping("/find-products/{id}")
    public String productsInCategory(@PathVariable("id") Long id, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();
        List<ProductDto> productDtos = productService.findByCategoryId(id);
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews", listView);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", productDtos.get(0).getCategory().getName());
        model.addAttribute("page", "Produtos");
        model.addAttribute("products", productDtos);
        return "shop-detail";
    }

    @GetMapping("/search-product")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        List<ProductDto> products = productService.searchProducts(keyword);
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews", listView);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Procurar Produtos");
        model.addAttribute("page", "Resultado Pesquisa Produtos");
        model.addAttribute("products", products);
        return "products";
    }
}
