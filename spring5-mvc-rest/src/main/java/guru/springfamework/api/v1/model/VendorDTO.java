package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jt on 10/6/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    @ApiModelProperty(notes = "vendor name", required = true)
    private String name;

    @ApiModelProperty(value = "URL to GET the vendor from" ,example = "/api/v1/vendors/123")
    @JsonProperty("vendor_url")
    private String vendorUrl;

}
