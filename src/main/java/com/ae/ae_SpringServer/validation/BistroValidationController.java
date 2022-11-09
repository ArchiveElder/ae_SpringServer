package com.ae.ae_SpringServer.validation;

import com.ae.ae_SpringServer.exception.AeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static com.ae.ae_SpringServer.exception.CodeAndMessage.*;

@Controller
@RequiredArgsConstructor
public class BistroValidationController {
    public void validateCategoryMain(String mainCategory) {
        if(mainCategory.isEmpty() || mainCategory.equals("")) {
            throw new AeException(MAIN_CATEGORY_NO_CONTENT);
        }

        if(mainCategory.length() > 10) {
            throw new AeException(MAIN_CATEGORY_LONG_CONTENT);
        }
    }

    public void validateCategoryMiddle(String middleCategory) {
        if(middleCategory.isEmpty() || middleCategory.equals("")) {
            throw new AeException(MIDDLE_CATEGORY_NO_CONTENT);
        }

        if(middleCategory.length() > 10) {
            throw new AeException(MIDDLE_CATEGORY_LONG_CONTENT);
        }
    }

    public void validateSiteWide(String siteWide) {
        if(siteWide.isEmpty() || siteWide.equals("")) {
            throw new AeException(SITE_WIDE_NO_CONTENT);
        }
        if(siteWide.length() > 45) {
            throw new AeException(SITE_WIDE_LONG_CONTENT);
        }
    }

    public void validateSiteMiddle(String siteMiddle) {
        if(siteMiddle.isEmpty() || siteMiddle.equals("")) {
            throw new AeException(SITE_MIDDLE_NO_CONTENT);
        }
        if(siteMiddle.length() > 45) {
            throw new AeException(SITE_MIDDLE_LONG_CONTENT);
        }
    }
}
