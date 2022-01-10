import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { getEntities as getBankTransactions } from 'app/entities/bank-transaction/bank-transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rebase-history.reducer';
import { IRebaseHistory } from 'app/shared/model/rebase-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const RebaseHistoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankTransactions = useAppSelector(state => state.bankTransaction.entities);
  const rebaseHistoryEntity = useAppSelector(state => state.rebaseHistory.entity);
  const loading = useAppSelector(state => state.rebaseHistory.loading);
  const updating = useAppSelector(state => state.rebaseHistory.updating);
  const updateSuccess = useAppSelector(state => state.rebaseHistory.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/rebase-history');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankTransactions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.runDate = convertDateTimeToServer(values.runDate);

    const entity = {
      ...rebaseHistoryEntity,
      ...values,
      transactionId: bankTransactions.find(it => it.id.toString() === values.transactionId.toString()),
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
          runDate: displayDefaultDateTime(),
        }
      : {
          oldCurrency: 'EUR',
          newCurrency: 'EUR',
          ...rebaseHistoryEntity,
          runDate: convertDateTimeFromServer(rebaseHistoryEntity.runDate),
          transactionId: rebaseHistoryEntity?.transactionId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.rebaseHistory.home.createOrEditLabel" data-cy="RebaseHistoryCreateUpdateHeading">
            <Translate contentKey="akountsApp.rebaseHistory.home.createOrEditLabel">Create or edit a RebaseHistory</Translate>
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
                  id="rebase-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.rebaseHistory.oldValue')}
                id="rebase-history-oldValue"
                name="oldValue"
                data-cy="oldValue"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.rebaseHistory.oldCurrency')}
                id="rebase-history-oldCurrency"
                name="oldCurrency"
                data-cy="oldCurrency"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('akountsApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.rebaseHistory.newValue')}
                id="rebase-history-newValue"
                name="newValue"
                data-cy="newValue"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.rebaseHistory.newCurrency')}
                id="rebase-history-newCurrency"
                name="newCurrency"
                data-cy="newCurrency"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('akountsApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.rebaseHistory.runDate')}
                id="rebase-history-runDate"
                name="runDate"
                data-cy="runDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="rebase-history-transactionId"
                name="transactionId"
                data-cy="transactionId"
                label={translate('akountsApp.rebaseHistory.transactionId')}
                type="select"
              >
                <option value="" key="0" />
                {bankTransactions
                  ? bankTransactions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rebase-history" replace color="info">
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

export default RebaseHistoryUpdate;
