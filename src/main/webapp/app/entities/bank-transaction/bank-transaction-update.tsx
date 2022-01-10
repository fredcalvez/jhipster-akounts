import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankCategory } from 'app/shared/model/bank-category.model';
import { getEntities as getBankCategories } from 'app/entities/bank-category/bank-category.reducer';
import { IBankAccount } from 'app/shared/model/bank-account.model';
import { getEntities as getBankAccounts } from 'app/entities/bank-account/bank-account.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-transaction.reducer';
import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const BankTransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankCategories = useAppSelector(state => state.bankCategory.entities);
  const bankAccounts = useAppSelector(state => state.bankAccount.entities);
  const bankTransactionEntity = useAppSelector(state => state.bankTransaction.entity);
  const loading = useAppSelector(state => state.bankTransaction.loading);
  const updating = useAppSelector(state => state.bankTransaction.updating);
  const updateSuccess = useAppSelector(state => state.bankTransaction.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/bank-transaction');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankCategories({}));
    dispatch(getBankAccounts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.categorizedDate = convertDateTimeToServer(values.categorizedDate);
    values.addDate = convertDateTimeToServer(values.addDate);
    values.rebasedDate = convertDateTimeToServer(values.rebasedDate);
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...bankTransactionEntity,
      ...values,
      catId: bankCategories.find(it => it.id.toString() === values.catId.toString()),
      accountId: bankAccounts.find(it => it.id.toString() === values.accountId.toString()),
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
          categorizedDate: displayDefaultDateTime(),
          addDate: displayDefaultDateTime(),
          rebasedDate: displayDefaultDateTime(),
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          localCurrency: 'EUR',
          currency: 'EUR',
          ...bankTransactionEntity,
          categorizedDate: convertDateTimeFromServer(bankTransactionEntity.categorizedDate),
          addDate: convertDateTimeFromServer(bankTransactionEntity.addDate),
          rebasedDate: convertDateTimeFromServer(bankTransactionEntity.rebasedDate),
          createdOn: convertDateTimeFromServer(bankTransactionEntity.createdOn),
          updatedOn: convertDateTimeFromServer(bankTransactionEntity.updatedOn),
          catId: bankTransactionEntity?.catId?.id,
          accountId: bankTransactionEntity?.accountId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankTransaction.home.createOrEditLabel" data-cy="BankTransactionCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankTransaction.home.createOrEditLabel">Create or edit a BankTransaction</Translate>
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
                  id="bank-transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankTransaction.transactionId')}
                id="bank-transaction-transactionId"
                name="transactionId"
                data-cy="transactionId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.transactionDate')}
                id="bank-transaction-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="date"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.description')}
                id="bank-transaction-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.localAmount')}
                id="bank-transaction-localAmount"
                name="localAmount"
                data-cy="localAmount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.localCurrency')}
                id="bank-transaction-localCurrency"
                name="localCurrency"
                data-cy="localCurrency"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('akountsApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.bankTransaction.amount')}
                id="bank-transaction-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.currency')}
                id="bank-transaction-currency"
                name="currency"
                data-cy="currency"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('akountsApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.bankTransaction.note')}
                id="bank-transaction-note"
                name="note"
                data-cy="note"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.year')}
                id="bank-transaction-year"
                name="year"
                data-cy="year"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.month')}
                id="bank-transaction-month"
                name="month"
                data-cy="month"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.week')}
                id="bank-transaction-week"
                name="week"
                data-cy="week"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.categorizedDate')}
                id="bank-transaction-categorizedDate"
                name="categorizedDate"
                data-cy="categorizedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.addDate')}
                id="bank-transaction-addDate"
                name="addDate"
                data-cy="addDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.checked')}
                id="bank-transaction-checked"
                name="checked"
                data-cy="checked"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.rebasedDate')}
                id="bank-transaction-rebasedDate"
                name="rebasedDate"
                data-cy="rebasedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.deleted')}
                id="bank-transaction-deleted"
                name="deleted"
                data-cy="deleted"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.tag')}
                id="bank-transaction-tag"
                name="tag"
                data-cy="tag"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.createdOn')}
                id="bank-transaction-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.updatedOn')}
                id="bank-transaction-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bankTransaction.version')}
                id="bank-transaction-version"
                name="version"
                data-cy="version"
                type="text"
              />
              <ValidatedField
                id="bank-transaction-catId"
                name="catId"
                data-cy="catId"
                label={translate('akountsApp.bankTransaction.catId')}
                type="select"
              >
                <option value="" key="0" />
                {bankCategories
                  ? bankCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="bank-transaction-accountId"
                name="accountId"
                data-cy="accountId"
                label={translate('akountsApp.bankTransaction.accountId')}
                type="select"
              >
                <option value="" key="0" />
                {bankAccounts
                  ? bankAccounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-transaction" replace color="info">
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

export default BankTransactionUpdate;
