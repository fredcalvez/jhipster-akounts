import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './plaid-transaction.reducer';
import { IPlaidTransaction } from 'app/shared/model/plaid-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidTransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plaidTransactionEntity = useAppSelector(state => state.plaidTransaction.entity);
  const loading = useAppSelector(state => state.plaidTransaction.loading);
  const updating = useAppSelector(state => state.plaidTransaction.updating);
  const updateSuccess = useAppSelector(state => state.plaidTransaction.updateSuccess);
  const handleClose = () => {
    props.history.push('/plaid-transaction');
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

    const entity = {
      ...plaidTransactionEntity,
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
        }
      : {
          ...plaidTransactionEntity,
          transactionDate: convertDateTimeFromServer(plaidTransactionEntity.transactionDate),
          addedDate: convertDateTimeFromServer(plaidTransactionEntity.addedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.plaidTransaction.home.createOrEditLabel" data-cy="PlaidTransactionCreateUpdateHeading">
            <Translate contentKey="akountsApp.plaidTransaction.home.createOrEditLabel">Create or edit a PlaidTransaction</Translate>
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
                  id="plaid-transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.transactionId')}
                id="plaid-transaction-transactionId"
                name="transactionId"
                data-cy="transactionId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.transactionType')}
                id="plaid-transaction-transactionType"
                name="transactionType"
                data-cy="transactionType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.accountId')}
                id="plaid-transaction-accountId"
                name="accountId"
                data-cy="accountId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.amount')}
                id="plaid-transaction-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.isoCurrencyCode')}
                id="plaid-transaction-isoCurrencyCode"
                name="isoCurrencyCode"
                data-cy="isoCurrencyCode"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.transactionDate')}
                id="plaid-transaction-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.name')}
                id="plaid-transaction-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.originalDescription')}
                id="plaid-transaction-originalDescription"
                name="originalDescription"
                data-cy="originalDescription"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.pending')}
                id="plaid-transaction-pending"
                name="pending"
                data-cy="pending"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.pendingTransactionId')}
                id="plaid-transaction-pendingTransactionId"
                name="pendingTransactionId"
                data-cy="pendingTransactionId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.addedDate')}
                id="plaid-transaction-addedDate"
                name="addedDate"
                data-cy="addedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.plaidTransaction.checked')}
                id="plaid-transaction-checked"
                name="checked"
                data-cy="checked"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plaid-transaction" replace color="info">
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

export default PlaidTransactionUpdate;
