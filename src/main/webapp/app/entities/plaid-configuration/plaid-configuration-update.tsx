import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './plaid-configuration.reducer';
import { IPlaidConfiguration } from 'app/shared/model/plaid-configuration.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidConfigurationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plaidConfigurationEntity = useAppSelector(state => state.plaidConfiguration.entity);
  const loading = useAppSelector(state => state.plaidConfiguration.loading);
  const updating = useAppSelector(state => state.plaidConfiguration.updating);
  const updateSuccess = useAppSelector(state => state.plaidConfiguration.updateSuccess);
  const handleClose = () => {
    props.history.push('/plaid-configuration');
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
    const entity = {
      ...plaidConfigurationEntity,
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
      ? {}
      : {
          ...plaidConfigurationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.plaidConfiguration.home.createOrEditLabel" data-cy="PlaidConfigurationCreateUpdateHeading">
            <Translate contentKey="akountsApp.plaidConfiguration.home.createOrEditLabel">Create or edit a PlaidConfiguration</Translate>
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
                  id="plaid-configuration-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.plaidConfiguration.environement')}
                id="plaid-configuration-environement"
                name="environement"
                data-cy="environement"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidConfiguration.key')}
                id="plaid-configuration-key"
                name="key"
                data-cy="key"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidConfiguration.value')}
                id="plaid-configuration-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plaid-configuration" replace color="info">
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

export default PlaidConfigurationUpdate;
