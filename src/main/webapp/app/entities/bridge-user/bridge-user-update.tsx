import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bridge-user.reducer';
import { IBridgeUser } from 'app/shared/model/bridge-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BridgeUserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bridgeUserEntity = useAppSelector(state => state.bridgeUser.entity);
  const loading = useAppSelector(state => state.bridgeUser.loading);
  const updating = useAppSelector(state => state.bridgeUser.updating);
  const updateSuccess = useAppSelector(state => state.bridgeUser.updateSuccess);
  const handleClose = () => {
    props.history.push('/bridge-user');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.lastLoginDate = convertDateTimeToServer(values.lastLoginDate);

    const entity = {
      ...bridgeUserEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          lastLoginDate: displayDefaultDateTime(),
        }
      : {
          ...bridgeUserEntity,
          lastLoginDate: convertDateTimeFromServer(bridgeUserEntity.lastLoginDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bridgeUser.home.createOrEditLabel" data-cy="BridgeUserCreateUpdateHeading">
            <Translate contentKey="akountsApp.bridgeUser.home.createOrEditLabel">Create or edit a BridgeUser</Translate>
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
                  id="bridge-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bridgeUser.uuid')}
                id="bridge-user-uuid"
                name="uuid"
                data-cy="uuid"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeUser.email')}
                id="bridge-user-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeUser.pass')}
                id="bridge-user-pass"
                name="pass"
                data-cy="pass"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeUser.lastLoginDate')}
                id="bridge-user-lastLoginDate"
                name="lastLoginDate"
                data-cy="lastLoginDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bridge-user" replace color="info">
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

export default BridgeUserUpdate;
