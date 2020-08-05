package ru.simbirsoft.warehouse_management.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.WriteoffDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WriteoffCreateDto;
import ru.simbirsoft.warehouse_management.service.WriteoffService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Writeoff controller (creating, deleting, confirming)")
public class WriteoffController {
    private final WriteoffService writeoffService;

    @ApiOperation(value = "Create new writeoff. Returns dto of created writeoff ")
    @PostMapping("${url.writeoff.new}")
    public WriteoffDto createInvoice(@RequestBody @Valid WriteoffCreateDto writeoffCreateDto) {
        return writeoffService.createWriteoff(writeoffCreateDto);
    }


    @ApiOperation(value = "Get writeoff by id")
    @GetMapping("${url.writeoff}/{id}")
    public WriteoffDto getById(@ApiParam("Id of the writeoff") @PathVariable Long id) {
        return writeoffService.getWriteoffById(id);
    }

    @ApiOperation(value = "Get invoice by id")
    @DeleteMapping("${url.writeoff}/{id}")
    public ResponseEntity<Long> deleteInvoice(@ApiParam("Id of the writeoff") @PathVariable Long id) {
        writeoffService.deleteById(id);
        return ResponseEntity.ok(id);
    }

    @ApiOperation(value = "Confirm invoice by id")
    @PostMapping("${url.writeoff.confirm}/{id}")
    public ResponseEntity<String> confirmInvoice(@ApiParam("id of the writeoff to confirm") @PathVariable Long id){
        writeoffService.confirmWriteoff(id);
        return ResponseEntity.ok("Writeoff confirmed, items are written off");
    }

    @ApiOperation(value = "Get all not confirmed writeoff")
    @GetMapping("${url.writeoff.notConfirmed}")
    public List<WriteoffDto> getNotConfirmed(){
        return writeoffService.getNotConfirmed();
    }


}
