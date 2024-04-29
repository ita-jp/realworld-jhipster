import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './comment.reducer';

export const CommentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commentEntity = useAppSelector(state => state.comment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentDetailsHeading">
          <Translate contentKey="realworldjdlApp.comment.detail.title">Comment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="realworldjdlApp.comment.id">Id</Translate>
            </span>
          </dt>
          <dd>{commentEntity.id}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="realworldjdlApp.comment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{commentEntity.createdAt ? <TextFormat value={commentEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="realworldjdlApp.comment.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{commentEntity.updatedAt ? <TextFormat value={commentEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="body">
              <Translate contentKey="realworldjdlApp.comment.body">Body</Translate>
            </span>
          </dt>
          <dd>{commentEntity.body}</dd>
          <dt>
            <Translate contentKey="realworldjdlApp.comment.user">User</Translate>
          </dt>
          <dd>{commentEntity.user ? commentEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="realworldjdlApp.comment.article">Article</Translate>
          </dt>
          <dd>{commentEntity.article ? commentEntity.article.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comment/${commentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentDetail;
