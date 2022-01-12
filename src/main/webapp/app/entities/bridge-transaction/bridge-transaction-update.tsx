import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bridge-transaction.reducer';
import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const BridgeTransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bridgeTransactionEntity = useAppSelector(state => state.bridgeTransaction.entity);
  const loading = useAppSelector(state => state.bridgeTransaction.loading);
  const updating = useAppSelector(state => state.bridgeTransaction.updating);
  const updateSuccess = useAppSelector(state => state.bridgeTransaction.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/bridge-transaction');
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
    values.transactionDate = convertDateTimeToServer(values.transactionDate);
    values.addedDate = convertDateTimeToServer(values.addedDate);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...bridgeTransactionEntity,
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
          transactionDate: displayDefaultDateTime(),
          addedDate: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          isoCurrencyCode: 'AED',
          ...bridgeTransactionEntity,
          transactionDate: convertDateTimeFromServer(bridgeTransactionEntity.transactionDate),
          addedDate: convertDateTimeFromServer(bridgeTransactionEntity.addedDate),
          updatedAt: convertDateTimeFromServer(bridgeTransactionEntity.updatedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bridgeTransaction.home.createOrEditLabel" data-cy="BridgeTransactionCreateUpdateHeading">
            <Translate contentKey="akountsApp.bridgeTransaction.home.createOrEditLabel">Create or edit a BridgeTransaction</Translate>
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
                  id="bridge-transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.transactionId')}
                id="bridge-transaction-transactionId"
                name="transactionId"
                data-cy="transactionId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.transactionType')}
                id="bridge-transaction-transactionType"
                name="transactionType"
                data-cy="transactionType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.accountId')}
                id="bridge-transaction-accountId"
                name="accountId"
                data-cy="accountId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.amount')}
                id="bridge-transaction-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.isoCurrencyCode')}
                id="bridge-transaction-isoCurrencyCode"
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
                label={translate('akountsApp.bridgeTransaction.transactionDate')}
                id="bridge-transaction-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.description')}
                id="bridge-transaction-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.isFuture')}
                id="bridge-transaction-isFuture"
                name="isFuture"
                data-cy="isFuture"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.isDeleted')}
                id="bridge-transaction-isDeleted"
                name="isDeleted"
                data-cy="isDeleted"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.addedDate')}
                id="bridge-transaction-addedDate"
                name="addedDate"
                data-cy="addedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.updatedAt')}
                id="bridge-transaction-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeTransaction.checked')}
                id="bridge-transaction-checked"
                name="checked"
                data-cy="checked"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bridge-transaction" replace color="info">
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

export default BridgeTransactionUpdate;
