package me.jun.guestbook.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.presentation.CommentController;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.post.presentation.PostController;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static me.jun.guestbook.utils.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class EntityModelUtils {

    public static EntityModel<PostResponse> postEntityModel(PostResponse postResponse) {
        EntityModel<PostResponse> entityModel = EntityModel.of(postResponse);
        WebMvcLinkBuilder controllerLink = linkTo(PostController.class);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(postResponse.getId());

        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(controllerLink.withRel(CREATE_POST));
        entityModel.add(selfLinkBuilder.withRel(GET_POST));
        entityModel.add(selfLinkBuilder.withRel(UPDATE_POST));
        entityModel.add(selfLinkBuilder.withRel(DELETE_POST));
        return entityModel;
    }

    public static RepresentationModel postEntityModel() {
        return new RepresentationModel<>()
                        .add(linkTo(PostController.class).withSelfRel())
                        .add(linkTo(PostController.class).withRel(CREATE_POST));
    }

    public static EntityModel<CommentResponse> commentEntityModel(CommentResponse commentResponse) {
        EntityModel<CommentResponse> entityModel = EntityModel.of(commentResponse);
        WebMvcLinkBuilder controllerLink = linkTo(CommentController.class);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(commentResponse.getId());

        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(controllerLink.withRel(CREATE_COMMENT));
        entityModel.add(selfLinkBuilder.withRel(GET_COMMENT));
        entityModel.add(selfLinkBuilder.withRel(UPDATE_COMMENT));
        entityModel.add(selfLinkBuilder.withRel(DELETE_COMMENT));
        return entityModel;
    }

    public static RepresentationModel commentEntityModel() {
        return new RepresentationModel<>()
                .add(linkTo(CommentController.class).withSelfRel())
                .add(linkTo(CommentController.class).withRel(CREATE_COMMENT));
    }
}
