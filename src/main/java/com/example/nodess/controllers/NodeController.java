package com.example.nodess.controllers;

import com.example.nodess.LocalAnatation.CurrentUser;
import com.example.nodess.entities.User;
import com.example.nodess.entities.vm.NodeSubmitVm;
import com.example.nodess.entities.vm.NodeVM;
import com.example.nodess.services.NodeService;
import com.example.nodess.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/1.0")
public class NodeController {
        NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }
    @PostMapping(path = "/node")
    GenericResponse saveNode(@Valid @RequestBody NodeSubmitVm node, @CurrentUser User user){
        nodeService.save(node,user);
        return new GenericResponse("Message Saved!");
    }
    @GetMapping(path = "/node")
    Page<NodeVM> getNodes(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        return nodeService.getNodes(pageable).map(NodeVM::new);
    }
    @GetMapping(path ={ "/node/{id:[1-9]+}","/users/{username}/nodes/{id:[1-9]+}"})
    ResponseEntity<?> getNodesRelative(
            @PageableDefault(sort = "id",direction = Sort.Direction.DESC)
            @PathVariable(required = false) String username,
             Pageable pageable, @PathVariable long id,
             @RequestParam(name = "count",required = false, defaultValue = "false") boolean count,
             @RequestParam(name = "direction",defaultValue = "before") String direction){
        if (count) {
            long newNodeCount = nodeService.getNewNodesCount(id,username);
            Map<String,Long> response = new HashMap<>();
            response.put("count",newNodeCount);
            return ResponseEntity.ok(response);
        }
        if (direction.equals("after")){
            List<NodeVM> nodeVMS = nodeService.getNewNodes(id,username,pageable.
            getSort()).stream().map(NodeVM::new).toList();
            return ResponseEntity.ok(nodeVMS);
        }
        return  ResponseEntity.ok(nodeService.getOldNodes(id,username,pageable).map(NodeVM::new));
    }
    @GetMapping(path = "/users/{username}/nodes")
    Page<NodeVM> getUserNodes(@PathVariable String username, @PageableDefault(sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable){
                return nodeService.getNodeOfUser(username,pageable).map(NodeVM::new);
    }

    @DeleteMapping(path = "/node/{id:[1-9]+}")
    @PreAuthorize("@nodeSecurityService.isAllowedToDelete(#id,principal)")
    GenericResponse deleteNode(@PathVariable long id){
        nodeService.delete(id);
        return new GenericResponse("Nodes Removed");

    }


}
