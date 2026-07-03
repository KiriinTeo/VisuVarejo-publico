package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesUseCase {

    private final CategoryRepository categoryRepository;
    private final CurrentUserProvider currentUserProvider;

    public List<Category> execute() {

        UUID companyId = currentUserProvider.getCompanyId();
        
        return categoryRepository.findByCompanyId(companyId);
    }
}
