package com.restaurant.service;

import com.restaurant.dto.MenuCreateDTO;
import com.restaurant.dto.MenuDTO;
import com.restaurant.entity.Menu;
import com.restaurant.mapper.MenuMapper;
import com.restaurant.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper mapper = MenuMapper.INSTANCE;
    public List<MenuDTO> getMenu() {
//        log.info("Parsed menu from DB");
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(mapper::toMenuDTO).toList();
    }

    public MenuDTO getMenu(Long id) {
//        log.info("Parsed menu from DB");
        Optional<Menu> menu = menuRepository.findById(id);
        return menu.map(mapper::toMenuDTO).orElse(null);
    }

    public MenuDTO createMenu(MenuCreateDTO menuCreateDTO) {
        Menu menu = mapper.fromMenuCreate(menuCreateDTO);
        Menu createdItem = menuRepository.save(menu);
        return mapper.toMenuDTO(createdItem);
    }

    public int updateMenu(MenuDTO menuToUpdate) {
        Optional<Menu> menuOptional = menuRepository.findById(menuToUpdate.getId());
        if(menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            menu.setName(menuToUpdate.getName());
            menu.setMealOrDrink(menuToUpdate.isMealOrDrink());
            menu.setCost(menuToUpdate.getCost());
            Menu updated = menuRepository.save(menu);
        }
        return menuOptional.isPresent() ? 1 : 0;
    }

    public boolean deleteMenu(Long menuId) {
        if(menuRepository.existsById(menuId)) {
            menuRepository.deleteById(menuId);
            return true;
        }
        return false;
    }
}
