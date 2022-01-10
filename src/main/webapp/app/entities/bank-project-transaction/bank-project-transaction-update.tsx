import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { getEntities as getBankTransactions } from 'app/entities/bank-transaction/bank-transaction.reducer';
import { IBankProject } from 'app/shared/model/bank-project.model';
import { getEntities as getBankProjects } from 'app/entities/bank-project/bank-project.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-project-transaction.reducer';
import { IBankProjectTransaction } from 'app/shared/model/bank-project-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankProjectTransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankTransactions = useAppSelector(state => state.bankTransaction.entities);
  const bankProjects = useAppSelector(state => state.bankProject.entities);
  const bankProjectTransactionEntity = useAppSelector(state => state.bankProjectTransaction.entity);
  const loading = useAppSelector(state => state.bankProjectTransaction.loading);
  const updating = useAppSelector(state => state.bankProjectTransaction.updating);
  const updateSuccess = useAppSelector(state => state.bankProjectTransaction.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-project-transaction');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankTransactions({}));
    dispatch(getBankProjects({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankProjectTransactionEntity,
      ...values,
      transactionId: bankTransactions.find(it => it.id.toString() === values.transactionId.toString()),
      projectId: bankProjects.find(it => it.id.toString() === values.projectId.toString()),
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
          ...bankProjectTransactionEntity,
          transactionId: bankProjectTransactionEntity?.transactionId?.id,
          projectId: bankProjectTransactionEntity?.projectId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankProjectTransaction.home.createOrEditLabel" data-cy="BankProjectTransactionCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankProjectTransaction.home.createOrEditLabel">
              Create or edit a BankProjectTransaction
            </Translate>
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
                  id="bank-project-transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="bank-project-transaction-transactionId"
                name="transactionId"
                data-cy="transactionId"
                label={translate('akountsApp.bankProjectTransaction.transactionId')}
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
              <ValidatedField
                id="bank-project-transaction-projectId"
                name="projectId"
                data-cy="projectId"
                label={translate('akountsApp.bankProjectTransaction.projectId')}
                type="select"
              >
                <option value="" key="0" />
                {bankProjects
                  ? bankProjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-project-transaction" replace color="info">
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

export default BankProjectTransactionUpdate;
