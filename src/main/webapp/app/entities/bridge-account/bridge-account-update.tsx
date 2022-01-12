import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bridge-account.reducer';
import { IBridgeAccount } from 'app/shared/model/bridge-account.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const BridgeAccountUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bridgeAccountEntity = useAppSelector(state => state.bridgeAccount.entity);
  const loading = useAppSelector(state => state.bridgeAccount.loading);
  const updating = useAppSelector(state => state.bridgeAccount.updating);
  const updateSuccess = useAppSelector(state => state.bridgeAccount.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/bridge-account');
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
      ...bridgeAccountEntity,
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
          isoCurrencyCode: 'AED',
          ...bridgeAccountEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bridgeAccount.home.createOrEditLabel" data-cy="BridgeAccountCreateUpdateHeading">
            <Translate contentKey="akountsApp.bridgeAccount.home.createOrEditLabel">Create or edit a BridgeAccount</Translate>
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
                  id="bridge-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bridgeAccount.bridgeAccountId')}
                id="bridge-account-bridgeAccountId"
                name="bridgeAccountId"
                data-cy="bridgeAccountId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeAccount.type')}
                id="bridge-account-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeAccount.status')}
                id="bridge-account-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeAccount.balance')}
                id="bridge-account-balance"
                name="balance"
                data-cy="balance"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeAccount.isoCurrencyCode')}
                id="bridge-account-isoCurrencyCode"
                name="isoCurrencyCode"
                data-cy="isoCurrencyCode"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('akountsApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.bridgeAccount.name')}
                id="bridge-account-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bridge-account" replace color="info">
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

export default BridgeAccountUpdate;
