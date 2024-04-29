import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article.reducer';

export const ArticleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const articleEntity = useAppSelector(state => state.article.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="articleDetailsHeading">
          <Translate contentKey="realworldjdlApp.article.detail.title">Article</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{articleEntity.id}</dd>
          <dt>
            <span id="slug">
              <Translate contentKey="realworldjdlApp.article.slug">Slug</Translate>
            </span>
          </dt>
          <dd>{articleEntity.slug}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="realworldjdlApp.article.title">Title</Translate>
            </span>
          </dt>
          <dd>{articleEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="realworldjdlApp.article.description">Description</Translate>
            </span>
          </dt>
          <dd>{articleEntity.description}</dd>
          <dt>
            <span id="body">
              <Translate contentKey="realworldjdlApp.article.body">Body</Translate>
            </span>
          </dt>
          <dd>{articleEntity.body}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="realworldjdlApp.article.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{articleEntity.createdAt ? <TextFormat value={articleEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="realworldjdlApp.article.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{articleEntity.updatedAt ? <TextFormat value={articleEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="realworldjdlApp.article.author">Author</Translate>
          </dt>
          <dd>{articleEntity.author ? articleEntity.author.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/article" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article/${articleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArticleDetail;
