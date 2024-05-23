package net.javaguides.springboot.ss;

import com.monytyz.billing.catalog.dto.discount.DiscountDto;
import com.monytyz.billing.catalog.dto.discount.DiscountListDto;
import com.monytyz.billing.catalog.dto.discount.DiscountPageDto;
import com.monytyz.billing.catalog.dto.discount.DiscountSaveDto;
import com.monytyz.billing.catalog.model.enums.DiscountStatus;
import com.monytyz.billing.catalog.service.DiscountService;
import com.monytyz.billing.common.constant.MessageConstant;
import com.monytyz.billing.common.dto.ResponseDto;
import com.monytyz.billing.common.validator.ValidId;
import com.monytyz.billing.util.MNPage;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;


@RestController
@Tag(name = "Discount")
@RequestMapping(path = "/api", produces = {"application/json"})
@Slf4j
@Validated
@lombok.RequiredArgsConstructor
@Timed
public class DiscountController {
    private final DiscountService discountService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = MessageConstant.CREATE_OBJECT, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DiscountDto.class))}),
            @ApiResponse(responseCode = "400", description = MessageConstant.BAD_REQUEST_MSG, content = @Content),
            @ApiResponse(responseCode = "500", description = MessageConstant.INTERNAL_SERVER_ERROR_MSG, content = @Content)})
    @PostMapping("/dis")
    public ResponseEntity<Object> createDiscount(@RequestBody @Valid DiscountSaveDto discountSaveDto) {
        return new ResponseEntity<>(discountDto, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageConstant.GET_PAGED_OBJECT, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DiscountDto.class))}),
            @ApiResponse(responseCode = "400", description = MessageConstant.BAD_REQUEST_MSG, content = @Content),
            @ApiResponse(responseCode = "404", description = MessageConstant.RESOURCE_NOT_FOUND_MSG, content = @Content),
            @ApiResponse(responseCode = "500", description = MessageConstant.INTERNAL_SERVER_ERROR_MSG, content = @Content)})
    @GetMapping("/discounts/{discountId}")
    public ResponseEntity<Object> getDiscountById(@Schema(example = "disct_jgPH8G33rELY6LQa") @PathVariable @ValidId(isBlank = false) String discountId) {
        return new ResponseEntity<>(discountDto, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageConstant.UPDATE_OBJECT_BY_ID, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DiscountDto.class))}),
            @ApiResponse(responseCode = "400", description = MessageConstant.BAD_REQUEST_MSG, content = @Content),
            @ApiResponse(responseCode = "500", description = MessageConstant.INTERNAL_SERVER_ERROR_MSG, content = @Content)})
    @PutMapping("/discounts/{discountId}")
    public ResponseEntity<Object> updateDiscountById(@RequestBody @Valid  DiscountSaveDto discountSaveDto, @Schema(example ="disc_jgPH8G33rELY6LQa")
                        @PathVariable @ValidId(isBlank = false) String  discountId) {
        return new ResponseEntity<>(updateDiscount, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageConstant.DISCOUNT_DELETE_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation =                    ResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = MessageConstant.BAD_REQUEST_MSG,content = @Content),
            @ApiResponse(responseCode = "500", description = MessageConstant.INTERNAL_SERVER_ERROR_MSG,  content = @Content)})
    @DeleteMapping("/discounts/{discountId}")
    public ResponseEntity<Object> deleteDiscountById(@Schema(example ="disc_jgPH8G33rELY6LQa")    @PathVariable @ValidId(isBlank = false) String   discountId) {
        discountService.deleteDiscountById(discountId);
        return new ResponseEntity<>(ResponseDto.toResponse(HttpStatus.OK, MessageConstant.DISCOUNT_DELETE_MSG), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageConstant.GET_PAGED_OBJECT, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DiscountPageDto.class))}),
            @ApiResponse(responseCode = "400", description = MessageConstant.BAD_REQUEST_MSG, content = @Content),
            @ApiResponse(responseCode = "500", description = MessageConstant.INTERNAL_SERVER_ERROR_MSG, content = @Content)
    })

    @Parameter(in = ParameterIn.QUERY, name = "id", schema = @Schema(type = "string", example = "disc_m0CB9LflojZxytfC"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "offeringId", schema = @Schema(type = "string", example = "offer_iJuVJLgQKIFc98RE"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "productId", schema = @Schema(type = "string", example = "prod_nStU73ZJahgBSxnh"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "name", schema = @Schema(type = "string", example = "Flat Discount"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "discountCode", schema = @Schema(type = "string", example = "CXTGN"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "quoteId", schema = @Schema(type = "string", example = "quote_cHCX0TFN79rDgDyg"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "startDate", schema = @Schema(type = "string", example = "2022-04-07"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "endDate", schema = @Schema(type = "string", example = "2022-05-07"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "accountId", schema = @Schema(type = "string", example = "acct_m0CB9LflojZxytfC"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "status", schema = @Schema(type = "string", example = "ACTIVE"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "durationType", schema = @Schema(type = "string", example = "12 Months"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "customDiscou", schema = @Schema(type = "string", example = "Flat"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "currency", schema = @Schema(type = "string", example = "USD"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "sort", schema = @Schema(type = "string", example = "name"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "currentPage", schema = @Schema(type = "number", example = "1"), required = false)
    @Parameter(in = ParameterIn.QUERY, name = "pageSize", schema = @Schema(type = "number", example = "10"), required = false)
    @GetMapping("/discounts")
    public ResponseEntity<MNPage<DiscountDto>> getListOfferingScopedDiscount(@Parameter(hidden = true) @RequestParam Map<String, String> customQueryParam) {

        var discountList = discountService.getDiscountList(customQueryParam);
        return new ResponseEntity<>(MNPage.fromPage(discountList), HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageConstant.GET_OBJECT_LIST, content = {  @Content(mediaType = "application/json", schema =                 @Schema(implementation = DiscountListDto.class))}),
            @ApiResponse(responseCode = "400", description =                    MessageConstant.BAD_REQUEST_MSG, content =            @Content),
            @ApiResponse(responseCode = "404", description =                    MessageConstant.RESOURCE_NOT_FOUND_MSG, content =            @Content),
            @ApiResponse(responseCode = "500", description =                    MessageConstant.INTERNAL_SERVER_ERROR_MSG, content =            @Content)})
    @GetMapping("/offerings/{offeringId}/discounts")
    public ResponseEntity<Object>
    getDiscountsByOfferingId(@Schema(example = "offer_iJuVJLgQKIFc98RE") @PathVariable @ValidId(isBlank = false) String offeringId,
                             @RequestParam(name = "accountId",      required = false)
                             @Schema(example =    "acct_iJuVJLgQKIFc98RE")     String accountId) {
        return new ResponseEntity<>(discountList, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deactivate  discount", content = { @Content(mediaType = "application/json", schema =@Schema(implementation = DiscountDto.class))}),
    @ApiResponse(responseCode = "400", description =
            MessageConstant.BAD_REQUEST_MSG, content =    @Content),    @ApiResponse(responseCode = "500", description =            MessageConstant.INTERNAL_SERVER_ERROR_MSG, content =    @Content) })

@PutMapping("/discounts/{discountId}/deactivate")
public ResponseEntity<Object> deactivateDiscountById(@Schema(example  = "disc_jgPH8G33rELY6LQa") @PathVariable @ValidId(isBlank = false)     String discountId) {
    return new ResponseEntity<>(discountDto, HttpStatus.OK);
}

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activate  discount", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DiscountDto.class))}),
        @ApiResponse(responseCode = "400", description = MessageConstant.BAD_REQUEST_MSG, content = @Content),
        @ApiResponse(responseCode = "500", description = MessageConstant.INTERNAL_SERVER_ERROR_MSG, content = @Content)
})

@PutMapping("/discounts/{discountId}/activate")
public ResponseEntity<Object> activateDiscountById(@Schema(example = "disc_iJuVJLgQKIFc98RE") @PathVariable @ValidId(isBlank = false) String discountId) {
    return new ResponseEntity<>(discountDto, HttpStatus.OK);
}
}