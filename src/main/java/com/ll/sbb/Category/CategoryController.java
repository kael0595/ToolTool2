//package com.ll.sbb.Category;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/category")
//public class CategoryController {
//
//    @Autowired
//    private final CategoryService categoryService;
//
//    @GetMapping("/main")
//    public List<Category> getMainCategories() {
//        return categoryService.getMainCategories();
//    }
//
//    @GetMapping("/{mainId}/sub")
//    public List<Category> getSubCategories(@PathVariable Long mainId) {
//        return categoryService.getSubCategories(mainId);
//    }
//
//}
