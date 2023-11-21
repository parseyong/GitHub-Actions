package com.example.todolist.Controller;

import com.example.todolist.DTO.CommonResponseDTO;
import com.example.todolist.DTO.ToDo.AddToDoReqDTO;
import com.example.todolist.DTO.ToDo.ReadToDoPreviewResDTO;
import com.example.todolist.DTO.ToDo.ReadToDoResDTO;
import com.example.todolist.DTO.ToDo.UpdateToDoReqDTO;
import com.example.todolist.Service.ToDoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "ToDo")
public class ToDoController {

    private final ToDoService toDoService;
    private final Long userId = 4l;
    @Autowired
    public ToDoController(ToDoService toDoService){
        this.toDoService=toDoService;
    }

    @GetMapping("/to-dos")
    @ApiOperation(value = "Todo 리스트 조회", notes = "모든 Todo의 Preview를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "전체ToDo 조회성공",response = ReadToDoPreviewResDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> readToDoPreviewList(){
        List<ReadToDoPreviewResDTO> readToDoPreviewResDTOList = toDoService.readToDoPreviewList();
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","todo preview조회 성공",readToDoPreviewResDTOList) );
    }

    @GetMapping("/to-dos/{toDoId}")
    @ApiOperation(value = "Todo 상세정보 조회", notes = "하나의 Todo에 대한 상세정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "개별ToDo 조회성공",response = ReadToDoResDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> readToDo(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출하는 로직
        */
        ReadToDoResDTO readToDoResDTO = toDoService.readToDo(toDoId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","todo 조회 성공",readToDoResDTO));
    }

    @PostMapping("/to-dos")
    @ApiOperation(value = "Todo 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "ToDo 추가성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> addToDo(@RequestBody @Valid AddToDoReqDTO addToDoReqDTO){
        /*
            토큰에서 userId값 추출하는 로직
        */
        addToDoReqDTO.setUserId(userId);
        toDoService.addToDo(addToDoReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDTO.of("CREATED","todo 추가 성공",null));
    }

    @PatchMapping("/to-dos/{toDoId}")
    @ApiOperation(value = "Todo 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ToDo 수정성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> updateToDo(@PathVariable Long toDoId, @RequestBody @Valid UpdateToDoReqDTO updateToDoReqDTO){
        /*
            토큰에서 userId값 추출하는 로직
        */
        updateToDoReqDTO.setUserId(userId);
        updateToDoReqDTO.setToDoId(toDoId);

        toDoService.updateToDo(updateToDoReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","todo 변경성공",null));
    }

    @DeleteMapping("/to-dos/{toDoId}")
    @ApiOperation(value = "Todo 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ToDo 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> deleteToDo(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출하는 로직
        */

        toDoService.deleteToDo(toDoId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","todo 삭제성공",null));
    }
}
