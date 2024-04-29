import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getUserExtendeds } from 'app/entities/user-extended/user-extended.reducer';
import { IUserExtended } from 'app/shared/model/user-extended.model';
import { getEntity, updateEntity, createEntity, reset } from './user-extended.reducer';

export const UserExtendedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userExtendeds = useAppSelector(state => state.userExtended.entities);
  const userExtendedEntity = useAppSelector(state => state.userExtended.entity);
  const loading = useAppSelector(state => state.userExtended.loading);
  const updating = useAppSelector(state => state.userExtended.updating);
  const updateSuccess = useAppSelector(state => state.userExtended.updateSuccess);

  const handleClose = () => {
    navigate('/user-extended');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserExtendeds({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...userExtendedEntity,
      ...values,
      users: mapIdList(values.users),
      follows: mapIdList(values.follows),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...userExtendedEntity,
          users: userExtendedEntity?.users?.map(e => e.id.toString()),
          follows: userExtendedEntity?.follows?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="realworldjdlApp.userExtended.home.createOrEditLabel" data-cy="UserExtendedCreateUpdateHeading">
            <Translate contentKey="realworldjdlApp.userExtended.home.createOrEditLabel">Create or edit a UserExtended</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="user-extended-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('realworldjdlApp.userExtended.user')}
                id="user-extended-user"
                data-cy="user"
                type="select"
                multiple
                name="users"
              >
                <option value="" key="0" />
                {userExtendeds
                  ? userExtendeds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('realworldjdlApp.userExtended.follow')}
                id="user-extended-follow"
                data-cy="follow"
                type="select"
                multiple
                name="follows"
              >
                <option value="" key="0" />
                {userExtendeds
                  ? userExtendeds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-extended" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UserExtendedUpdate;
