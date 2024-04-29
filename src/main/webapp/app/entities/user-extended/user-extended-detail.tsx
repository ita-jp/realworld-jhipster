import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-extended.reducer';

export const UserExtendedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userExtendedEntity = useAppSelector(state => state.userExtended.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userExtendedDetailsHeading">
          <Translate contentKey="realworldjdlApp.userExtended.detail.title">UserExtended</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userExtendedEntity.id}</dd>
          <dt>
            <Translate contentKey="realworldjdlApp.userExtended.user">User</Translate>
          </dt>
          <dd>
            {userExtendedEntity.users
              ? userExtendedEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {userExtendedEntity.users && i === userExtendedEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="realworldjdlApp.userExtended.follow">Follow</Translate>
          </dt>
          <dd>
            {userExtendedEntity.follows
              ? userExtendedEntity.follows.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {userExtendedEntity.follows && i === userExtendedEntity.follows.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-extended" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-extended/${userExtendedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserExtendedDetail;
