import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profile.reducer';

export const ProfileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profileEntity = useAppSelector(state => state.profile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profileDetailsHeading">
          <Translate contentKey="realworldjdlApp.profile.detail.title">Profile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profileEntity.id}</dd>
          <dt>
            <span id="bio">
              <Translate contentKey="realworldjdlApp.profile.bio">Bio</Translate>
            </span>
          </dt>
          <dd>{profileEntity.bio}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="realworldjdlApp.profile.image">Image</Translate>
            </span>
          </dt>
          <dd>{profileEntity.image}</dd>
          <dt>
            <Translate contentKey="realworldjdlApp.profile.user">User</Translate>
          </dt>
          <dd>{profileEntity.user ? profileEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="realworldjdlApp.profile.follower">Follower</Translate>
          </dt>
          <dd>
            {profileEntity.followers
              ? profileEntity.followers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {profileEntity.followers && i === profileEntity.followers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="realworldjdlApp.profile.followee">Followee</Translate>
          </dt>
          <dd>
            {profileEntity.followees
              ? profileEntity.followees.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {profileEntity.followees && i === profileEntity.followees.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profile/${profileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfileDetail;
