import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './plaid-account.reducer';
import { IPlaidAccount } from 'app/shared/model/plaid-account.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const PlaidAccountUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plaidAccountEntity = useAppSelector(state => state.plaidAccount.entity);
  const loading = useAppSelector(state => state.plaidAccount.loading);
  const updating = useAppSelector(state => state.plaidAccount.updating);
  const updateSuccess = useAppSelector(state => state.plaidAccount.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/plaid-account');
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
      ...plaidAccountEntity,
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
          ...plaidAccountEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.plaidAccount.home.createOrEditLabel" data-cy="PlaidAccountCreateUpdateHeading">
            <Translate contentKey="akountsApp.plaidAccount.home.createOrEditLabel">Create or edit a PlaidAccount</Translate>
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
                  id="plaid-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.plaidAccount.plaidAccountId')}
                id="plaid-account-plaidAccountId"
                name="plaidAccountId"
                data-cy="plaidAccountId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.itemId')}
                id="plaid-account-itemId"
                name="itemId"
                data-cy="itemId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.type')}
                id="plaid-account-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.subtype')}
                id="plaid-account-subtype"
                name="subtype"
                data-cy="subtype"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.balanceAvailable')}
                id="plaid-account-balanceAvailable"
                name="balanceAvailable"
                data-cy="balanceAvailable"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.balanceCurrent')}
                id="plaid-account-balanceCurrent"
                name="balanceCurrent"
                data-cy="balanceCurrent"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.isoCurrencyCode')}
                id="plaid-account-isoCurrencyCode"
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
                label={translate('akountsApp.plaidAccount.name')}
                id="plaid-account-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.officialName')}
                id="plaid-account-officialName"
                name="officialName"
                data-cy="officialName"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.iban')}
                id="plaid-account-iban"
                name="iban"
                data-cy="iban"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidAccount.bic')}
                id="plaid-account-bic"
                name="bic"
                data-cy="bic"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plaid-account" replace color="info">
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

export default PlaidAccountUpdate;
